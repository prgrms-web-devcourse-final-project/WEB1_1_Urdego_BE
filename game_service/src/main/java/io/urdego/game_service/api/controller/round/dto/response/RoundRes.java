package io.urdego.game_service.api.controller.round.dto.response;

import io.urdego.game_service.domain.entity.round.Round;

import java.util.List;

public record RoundRes(
        Long roundId,
        int roundTimer,
        List<String> contentUrls,
        String hint

) {
    public static RoundRes from(Long roundId, int roundTimer, List<String> contentUrls, String hint) {
        return new RoundRes(
                roundId,
                roundTimer,
                contentUrls,
                hint
        );
    }
}
