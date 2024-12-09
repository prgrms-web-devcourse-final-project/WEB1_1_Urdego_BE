package io.urdego.game_service.api.service.submission;

import io.urdego.game_service.api.controller.submission.dto.request.SubmissionReq;
import io.urdego.game_service.api.controller.submission.dto.response.SubmissionRes;
import io.urdego.game_service.api.service.game.GameServiceImpl;
import io.urdego.game_service.common.client.ContentServiceClient;
import io.urdego.game_service.common.client.GroupServiceClient;
import io.urdego.game_service.common.client.UserServiceClient;
import io.urdego.game_service.common.client.dto.request.UserReq;
import io.urdego.game_service.common.client.dto.response.ContentRes;
import io.urdego.game_service.common.client.dto.response.GroupInfoRes;
import io.urdego.game_service.common.client.dto.response.UserInfoRes;
import io.urdego.game_service.common.client.dto.response.UserRes;
import io.urdego.game_service.common.exception.ExceptionMessage;
import io.urdego.game_service.common.exception.content.ContentException;
import io.urdego.game_service.common.exception.round.RoundException;
import io.urdego.game_service.common.exception.user.UserException;
import io.urdego.game_service.domain.entity.game.Game;
import io.urdego.game_service.domain.entity.round.Round;
import io.urdego.game_service.domain.entity.round.repository.RoundRepository;
import io.urdego.game_service.domain.entity.submission.Submission;
import io.urdego.game_service.domain.entity.submission.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final RoundRepository roundRepository;
    private final GameServiceImpl gameServiceImpl;
    private final GroupServiceClient groupServiceClient;
    private final SubmissionRepository submissionRepository;
    private final UserServiceClient userServiceClient;
    private final ContentServiceClient contentServiceClient;
    private final RedissonClient redissonClient;

    // 답안 제출
    @Override
    public SubmissionRes submitAnswer(SubmissionReq request) {
        // Redis 기반 락 키 생성
        String lockKey = "submission:lock:" + request.roundId() + ":" + request.nickname();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean lockAcquired = lock.tryLock(15, 30, TimeUnit.SECONDS);
            if (lockAcquired) {
                log.info("제출 처리 락 획득 성공. roundId: {}, nickname: {}", request.roundId(), request.nickname());
            } else {
                log.info("제출 처리 락 획득 실패. roundId: {}, nickname: {}", request.roundId(), request.nickname());
                throw new IllegalStateException("다른 프로세스에서 제출을 처리중...");
            }

            // 닉네임으로 사용자 조회
            UserInfoRes userInfo = userServiceClient.getUserIdByNickname(new UserReq(request.nickname()));
            if (userInfo == null || userInfo.userId() == null) {
                log.warn("{} / nickname : {}", ExceptionMessage.USER_NOT_FOUND, request.nickname());
                throw new UserException(ExceptionMessage.USER_NOT_FOUND.getText());
            }

            Long userId = userInfo.userId();

            // 라운드에서 정답 컨텐츠 추출
            Round round = roundRepository.findById(request.roundId())
                    .orElseThrow(() -> new RoundException(ExceptionMessage.ROUND_NOT_FOUND.getText() + "roundId : " + request.roundId()));

            if (round.getContentIds().isEmpty()) {
                throw new ContentException(ExceptionMessage.ROUND_CONTENT_NOT_FOUND.getText() + "roundId : " + request.roundId());

            }

            Long contentId = round.getContentIds().get(0);
            ContentRes content = contentServiceClient.getContent(contentId);

            // 거리 계산
            double distance = calculateDistance(
                    request.latitude(),
                    request.longitude(),
                    content.latitude(),
                    content.longitude()
            );

            // 점수 계산
            int score = calculateScore(distance);

            // 제출 정보 저장
            Submission newSubmission = Submission.builder()
                    .latitude(request.latitude())
                    .longitude(request.longitude())
                    .score(score)
                    .userId(userId)
                    .roundId(request.roundId())
                    .build();

            submissionRepository.save(newSubmission);

            // 모든 플레이어 정보 및 총점 계산
            Game game = gameServiceImpl.findByGameIdOrThrowGameException(round.getGameId());
            GroupInfoRes groupInfo = groupServiceClient.getGroupInfo(game.getGroupId());

            List<SubmissionRes.UserSubmission> userSubmissions = groupInfo.invitedUserIds()
                    .stream()
                    .map(playerId -> {
                        UserRes user = userServiceClient.getUserById(playerId);
                        // 각 플레이어의 제출 정보
                        Submission existingSubmission = submissionRepository.findByUserIdAndRoundId(playerId, round.getRoundId()).orElse(null);

                        // 플레이어 총점 계산
                        int totalScore = submissionRepository.findAllByUserId(playerId)
                                .stream()
                                .filter(sub -> {
                                    Round userRound = roundRepository.findById(sub.getRoundId()).orElse(null);
                                    return userRound != null && userRound.getGameId().equals(game.getGameId());
                                })
                                .mapToInt(Submission::getScore)
                                .sum();

                        if (existingSubmission == null) {
                            return new SubmissionRes.UserSubmission(
                                    user.nickname(),
                                    0.0,
                                    0.0,
                                    0,
                                    totalScore
                            );
                        }

                        return new SubmissionRes.UserSubmission(
                                user.nickname(),
                                existingSubmission.getLatitude(),
                                existingSubmission.getLongitude(),
                                existingSubmission.getScore(),
                                totalScore
                        );
                    })
                    .toList();

            // 응답 생성
            return SubmissionRes.of(content, userSubmissions);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("제출 처리 락 획득 실패.", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("제출 처리 락 해제");
            }
        }
    }

    // 거리 계산 - 하버사인 공식(Haversine Formula)
    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // 거리 기반 점수 계산 - 선형 감소(Linear Decay)
    @Override
    public int calculateScore(double distance) {
        // 선형 감소 점수 계산
        final int maxScore = 1000; // 최대 점수
        final double maxDistance = 200.0; // 점수가 0이 되는 기준 거리 (10km)

        if (distance > maxDistance) {
            return 0; // 최대 거리를 초과하면 점수는 0점
        }

        // 선형 점수 계산 공식
        return (int) Math.max(0, maxScore - (distance / maxDistance) * maxScore);
    }
}
