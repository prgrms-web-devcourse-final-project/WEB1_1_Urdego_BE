package io.urdego.game_service.api.controller.submission.dto.response;

import io.urdego.game_service.common.client.dto.response.ContentRes;

import java.util.List;

public record SubmissionRes(
        Coordinate answerCoordinate,
        List<UserSubmission> submitCoordinates
) {
    public static SubmissionRes of(ContentRes content, List<UserSubmission> submissions) {
        return new SubmissionRes(
                new Coordinate(content.latitude(), content.longitude()),
                submissions
        );
    }

    public static record Coordinate(
            double latitude,
            double longitude
    ) {}

    public static record UserSubmission(
            String nickname,
            double latitude,
            double longitude,
            int score,
            int totalScore
    ) {}
}
