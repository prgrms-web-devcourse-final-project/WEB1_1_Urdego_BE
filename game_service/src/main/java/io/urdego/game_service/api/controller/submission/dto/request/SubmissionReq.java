package io.urdego.game_service.api.controller.submission.dto.request;

public record SubmissionReq(
        Long playerId,
        Long roundId,
        double latitude,
        double longitude,
        Long contentId
) {
}
