package io.urdego.game_service.api.service.round;

import io.urdego.game_service.api.controller.round.dto.request.RoundCreateReq;
import io.urdego.game_service.api.controller.round.dto.response.RoundRes;
import io.urdego.game_service.api.service.game.GameServiceImpl;
import io.urdego.game_service.common.client.ContentServiceClient;
import io.urdego.game_service.common.client.GroupServiceClient;
import io.urdego.game_service.common.client.dto.response.ContentRes;
import io.urdego.game_service.common.client.dto.response.GroupInfoRes;
import io.urdego.game_service.common.exception.ExceptionMessage;
import io.urdego.game_service.common.exception.content.ContentException;
import io.urdego.game_service.domain.entity.game.Game;
import io.urdego.game_service.domain.entity.round.Round;
import io.urdego.game_service.domain.entity.round.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoundServiceImpl implements RoundService{

    private final RoundRepository roundRepository;
    private final GameServiceImpl gameServiceImpl;
    private final ContentServiceClient contentServiceClient;
    private final GroupServiceClient groupServiceClient;
    private final RedissonClient redissonClient;

    // 라운드 생성
    @Override
    public RoundRes createRound(RoundCreateReq request) {
        // Redis 기반 락 키 생성
        String lockKey = "round:create:" + request.gameId() + ":" + request.roundNum();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (!lock.tryLock(15, 10, TimeUnit.SECONDS)) {
                log.warn("라운드 생성 락 획득 실패. gameId: {}, roundNum: {}", request.gameId(), request.roundNum());
                throw new IllegalStateException("다른 프로세스에서 라운드 생성을 진행중...");
            }

            // 비관적 락으로 중복 확인
            Optional<Round> existingRound = roundRepository.findByGameIdAndRoundNumForUpdate(request.gameId(), request.roundNum());
            if (existingRound.isPresent()) {
                log.warn("중복 라운드 생성이 감지되었습니다. gameId: {}, roundNum: {}", request.gameId(), request.roundNum());
                throw new IllegalStateException("이미 존재하는 라운드입니다.");
            }

            // 게임 정보 조회
            Long currentGameId = request.gameId();
            Game game = gameServiceImpl.findByGameIdOrThrowGameException(currentGameId);

            // 그룹에서 플레이어 ID 조회
            GroupInfoRes groupInfo = groupServiceClient.getGroupInfo(game.getGroupId());
            log.info("그룹 정보 불러오기. : {}", groupInfo);
            List<Long> playerIds = groupInfo.invitedUserIds();
            if (playerIds == null || playerIds.isEmpty()) {
                log.warn("해당 그룹 아이디로 초대된 유저가 없습니다. : {}", game.getGroupId());
                throw new IllegalArgumentException("초대된 유저가 없습니다.");
            }

            // 플레이어별 컨텐츠 가져오기
            List<ContentRes> contentList = contentServiceClient.getUserContents(playerIds);
            if (contentList.isEmpty()) {
                log.warn("{} / PlayerIds : {}", ExceptionMessage.ROUND_CONTENT_NOT_FOUND, playerIds);
                throw new ContentException(ExceptionMessage.ROUND_CONTENT_NOT_FOUND.getText());
            }

            // 위도, 경도를 기준으로 그룹화
            Map<String, List<ContentRes>> groupedByExactLocation = contentList.stream()
                    .collect(Collectors.groupingBy(content -> content.latitude() + "," + content.longitude()));

            // 그룹 중 하나를 랜덤으로 선택, 섞어서 최대 3개 선정
            Map.Entry<String, List<ContentRes>> selectedGroup = selectRandomGroup(groupedByExactLocation);
            List<ContentRes> selectedContents = selectedGroup.getValue();

            Collections.shuffle(selectedContents);
            List<ContentRes> limitedContents = selectedContents.stream()
                    .limit(3)
                    .toList();

            List<Long> contentIds = limitedContents.stream()
                    .map(ContentRes::contentId)
                    .toList();

            // 라운드 생성
            Round round = Round.builder()
                    .gameId(currentGameId)
                    .roundNum(request.roundNum())
                    .contentIds(contentIds)
                    .build();
            round = roundRepository.save(round);

            log.info("Round {} 생성. {} 개의 문제, 위치 정보: {}", round.getRoundNum(), limitedContents.size(), selectedGroup.getKey());

            // 응답 생성
            List<String> contentUrls = limitedContents.stream()
                    .map(ContentRes::url)
                    .toList();

            String hint = limitedContents.get(0).hint();
            return RoundRes.from(round.getRoundId(), round.getRoundNum(), game.getTimer(), contentUrls, hint);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("라운드 생성 락 획득 실패.", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private Map.Entry<String, List<ContentRes>> selectRandomGroup(Map<String, List<ContentRes>> groupedByLocation) {
        List<Map.Entry<String, List<ContentRes>>> groups = new ArrayList<>(groupedByLocation.entrySet());
        if (groups.isEmpty()) {
            throw new ContentException(ExceptionMessage.ROUND_CONTENT_NOT_FOUND.getText());
        }
        // 랜덤으로 그룹 선택
        return groups.get(new Random().nextInt(groups.size()));
    }

}
