package io.urdego.game_service.api.controller.round.dto.request;

public record RoundCreateReq(
        Long gameId,
        int roundNum
) {
}
