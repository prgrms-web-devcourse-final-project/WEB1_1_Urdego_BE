package io.urdego.game_service.api.service.submission;

import io.urdego.game_service.api.controller.submission.dto.request.SubmissionReq;
import io.urdego.game_service.api.controller.submission.dto.response.SubmissionRes;
import io.urdego.game_service.common.client.ContentServiceClient;
import io.urdego.game_service.common.client.dto.response.ContentRes;
import io.urdego.game_service.common.exception.ExceptionMessage;
import io.urdego.game_service.common.exception.content.ContentException;
import io.urdego.game_service.common.exception.round.RoundException;
import io.urdego.game_service.domain.entity.round.Round;
import io.urdego.game_service.domain.entity.round.repository.RoundRepository;
import io.urdego.game_service.domain.entity.submission.Submission;
import io.urdego.game_service.domain.entity.submission.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final RoundRepository roundRepository;
    private SubmissionRepository submissionRepository;
    private ContentServiceClient contentServiceClient;

    // 답안 제출
    @Override
    public SubmissionRes submitAnswer(SubmissionReq request) {
        // 1. 라운드에서 정답 컨텐츠 추출
        Round round = roundRepository.findById(request.roundId())
                .orElseThrow(() -> new RoundException(ExceptionMessage.ROUND_NOT_FOUND.getText() + "roundId : " + request.roundId()));

        if (round.getContentIds().isEmpty()) {
            throw new ContentException(ExceptionMessage.ROUND_CONTENT_NOT_FOUND.getText() + "roundId : " + request.roundId());

        }

        Long contentId = round.getContentIds().get(0);
        ContentRes content = contentServiceClient.getContent(contentId);

        // 2. 거리 계산
        double distance = calculateDistance(
                request.latitude(),
                request.longitude(),
                content.latitude(),
                content.longitude()
        );

        // 3. 점수 계산
        int score = calculateScore(distance);

        // 4. 제출
        Submission submission = Submission.builder()
                .latitude(request.latitude())
                .longitude(request.longitude())
                .score(score)
                .playerId(request.playerId())
                .roundId(request.roundId())
                .build();

        submission = submissionRepository.save(submission);

        return SubmissionRes.from(submission);

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
