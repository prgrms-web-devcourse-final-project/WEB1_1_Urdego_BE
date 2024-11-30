package io.urdego.game_service.api.controller.round.dto.response;

import io.urdego.game_service.domain.entity.round.Round;

public record RoundRes(
        Long gameId,
        Long roundId,
        int roundNum
) {
    public static RoundRes from(Round round) {
        return new RoundRes(
                round.getGameId(),
                round.getRoundId(),
                round.getRoundNum()
        );
    }
}
