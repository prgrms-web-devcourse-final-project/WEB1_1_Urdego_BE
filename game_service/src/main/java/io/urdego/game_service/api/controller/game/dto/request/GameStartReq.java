package io.urdego.game_service.api.controller.game.dto.request;

public record GameStartReq(
        Long groupId,
        int totalRounds,
        int timer,
        int playerCounts
) {
}
