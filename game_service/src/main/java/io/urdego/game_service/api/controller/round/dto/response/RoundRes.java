package io.urdego.game_service.api.controller.round.dto.response;

import java.util.List;

public record RoundRes(
        Long roundId,
        int roundNum,
        int roundTimer,
        List<String> contentUrls,
        String hint

) {
    public static RoundRes from(Long roundId, int roundNum,  int roundTimer, List<String> contentUrls, String hint) {
        return new RoundRes(
                roundId,
                roundNum,
                roundTimer,
                contentUrls,
                hint
        );
    }
}
