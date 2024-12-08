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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    // 라운드 생성
    @Override
    public RoundRes createRound(RoundCreateReq request) {
        // 1. 게임 정보 조회
        Long currentGameId = request.gameId();
        Game game = gameServiceImpl.findByGameIdOrThrowGameException(currentGameId);

        // 2. 그룹에서 플레이어 ID 조회
        GroupInfoRes groupInfo = groupServiceClient.getGroupInfo(game.getGroupId());
        log.info("Fetched group info: {}", groupInfo);
        List<Long> playerIds = groupInfo.invitedUserIds();
        if (playerIds == null || playerIds.isEmpty()) {
            log.warn("No invited users found for groupId: {}", game.getGroupId());
            throw new IllegalArgumentException("No invited users found");
        }

        // 3. 플레이어별 컨텐츠 가져오기
        List<ContentRes> contentList = contentServiceClient.getUserContents(playerIds);
        if (contentList.isEmpty()) {
            log.warn("{} / PlayerIds : {}", ExceptionMessage.ROUND_CONTENT_NOT_FOUND, playerIds);
            throw new ContentException(ExceptionMessage.ROUND_CONTENT_NOT_FOUND.getText());
        }

        // 4. 위도, 경도를 기준으로 그룹화
        Map<String, List<ContentRes>> groupedByExactLocation = contentList.stream()
                .collect(Collectors.groupingBy(content -> content.latitude() + "," + content.longitude()));

        // 5. 그룹 중 하나를 랜덤으로 선택, 섞어서 최대 3개 선정
        Map.Entry<String, List<ContentRes>> selectedGroup = selectRandomGroup(groupedByExactLocation);
        List<ContentRes> selectedContents = selectedGroup.getValue();

        Collections.shuffle(selectedContents);
        List<ContentRes> limitedContents = selectedContents.stream()
                .limit(3)
                .toList();

        List<Long> contentIds = limitedContents.stream()
                .map(ContentRes::contentId)
                .toList();

        // 6. 라운드 생성
        Round round = Round.builder()
                .gameId(currentGameId)
                .roundNum(request.roundNum())
                .contentIds(contentIds)
                .build();
        round = roundRepository.save(round);

        log.info("Round {} created with {} problems, location: {}", round.getRoundNum(), limitedContents.size(), selectedGroup.getKey());

        // 7. 응답 생성
        List<String> contentUrls = limitedContents.stream()
                .map(ContentRes::url)
                .toList();

        String hint = limitedContents.get(0).hint();
        return RoundRes.from(round.getRoundId(), round.getRoundNum(), game.getTimer(), contentUrls, hint);
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
