package io.urdego.game_service.api.controller.round.dto.response;

public record RoundStartRes(
        Long roundId,
        int roundNumber,
        int timer
) {
}
