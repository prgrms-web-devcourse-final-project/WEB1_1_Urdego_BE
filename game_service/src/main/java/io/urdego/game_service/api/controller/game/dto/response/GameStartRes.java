package io.urdego.game_service.api.controller.game.dto.response;

import io.urdego.game_service.domain.entity.game.Game;

public record GameStartRes(
        Long gameId,
        int totalRounds
) {
    public static GameStartRes from(Game game) {
        return new GameStartRes(
                game.getGameId(),
                game.getTotalRounds()
        );
    }
}
