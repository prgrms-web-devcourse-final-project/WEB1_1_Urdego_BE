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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final RoundRepository roundRepository;
    private final GameServiceImpl gameServiceImpl;
    private final GroupServiceClient groupServiceClient;
    private SubmissionRepository submissionRepository;
    private final UserServiceClient userServiceClient;
    private ContentServiceClient contentServiceClient;

    // 답안 제출
    @Override
    public SubmissionRes submitAnswer(SubmissionReq request) {
        // 1. 닉네임으로 사용자 조회
        UserInfoRes userInfo = userServiceClient.getUserIdByNickname(new UserReq(request.nickname()));
        if (userInfo == null || userInfo.userId() == null) {
            log.warn("{} / nickname : {}", ExceptionMessage.USER_NOT_FOUND, request.nickname());
            throw new UserException(ExceptionMessage.USER_NOT_FOUND.getText());
        }

        Long userId = userInfo.userId();

        // 2. 라운드에서 정답 컨텐츠 추출
        Round round = roundRepository.findById(request.roundId())
                .orElseThrow(() -> new RoundException(ExceptionMessage.ROUND_NOT_FOUND.getText() + "roundId : " + request.roundId()));

        if (round.getContentIds().isEmpty()) {
            throw new ContentException(ExceptionMessage.ROUND_CONTENT_NOT_FOUND.getText() + "roundId : " + request.roundId());

        }

        Long contentId = round.getContentIds().get(0);
        ContentRes content = contentServiceClient.getContent(contentId);

        // 3. 거리 계산
        double distance = calculateDistance(
                request.latitude(),
                request.longitude(),
                content.latitude(),
                content.longitude()
        );

        // 4. 점수 계산
        int score = calculateScore(distance);

        // 5. 제출 정보 저장
        Submission submission = Submission.builder()
                .latitude(request.latitude())
                .longitude(request.longitude())
                .score(score)
                .userId(userId)
                .roundId(request.roundId())
                .build();

        submissionRepository.save(submission);

        // 6. 모든 플레이어 정보 및 총점 계산
        Game game = gameServiceImpl.findByGameIdOrThrowGameException(round.getGameId());
        GroupInfoRes groupInfo = groupServiceClient.getGroupInfo(game.getGroupId());

        List<SubmissionRes.PlayerSubmission> playerSubmissions = groupInfo.invitedUsers()
                .stream()
                .map(id -> {
                    UserRes user = userServiceClient.getUserById(id);
                    // 각 플레이어의 제출 정보
                    Submission playerSubmission = submissionRepository.findByPlayerIdAndRoundId(userId, round.getRoundId())
                            .orElseThrow(() -> new IllegalArgumentException("Submission not found for playerId: " + userId));

                    // 플레이어 총점 계산
                    int totalScore = submissionRepository.findAllByPlayerId(userId)
                            .stream()
                            .mapToInt(Submission::getScore)
                            .sum();

                    return new SubmissionRes.PlayerSubmission(
                            user.nickname(),
                            playerSubmission.getLatitude(),
                            playerSubmission.getLongitude(),
                            playerSubmission.getScore(),
                            totalScore
                    );
                })
                .toList();

        // 6. 응답 생성
        return SubmissionRes.of(content, playerSubmissions);

    }

    // 거리 계산 - 하버사인 공식
    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // 거리 기반 점수 계산
    @Override
    public int calculateScore(double distance) {
        if (distance < 1) {
            return 100;
        } else if (distance < 5) {
            return 80;
        } else if (distance < 10) {
            return 50;
        } else {
            return 0;
        }
    }
}
