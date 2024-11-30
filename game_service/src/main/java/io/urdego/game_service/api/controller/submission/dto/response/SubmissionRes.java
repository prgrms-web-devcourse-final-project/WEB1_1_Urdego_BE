package io.urdego.game_service.api.controller.submission.dto.response;

import io.urdego.game_service.domain.entity.submission.Submission;

public record SubmissionRes(
        Long submissionId,
        Long playerId,
        Long roundId,
        double latitude,
        double longitude,
        int score
) {
    public static SubmissionRes from(Submission submission) {
        return new SubmissionRes(
                submission.getSubmissionId(),
                submission.getPlayerId(),
                submission.getRoundId(),
                submission.getLatitude(),
                submission.getLongitude(),
                submission.getScore()
        );
    }
}
