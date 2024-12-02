package io.urdego.game_service.api.controller.submission.dto.request;

import java.util.List;

public record SubmissionReq(
        String nickname,
        Long roundId,
        List<Double> coordinate
) {
    public double latitude() {
        return coordinate.get(0);
    }

    public double longitude() {
        return coordinate.get(1);
    }
}