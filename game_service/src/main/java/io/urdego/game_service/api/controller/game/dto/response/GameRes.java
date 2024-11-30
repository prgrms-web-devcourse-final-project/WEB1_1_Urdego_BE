package io.urdego.game_service.api.controller.game.dto.response;

import io.urdego.game_service.domain.entity.game.Game;

import java.time.LocalDateTime;

public record GameRes(
        Long gameId,
        Long groupId,
        int totalRounds,
        int timer,
        boolean inProgress,
        LocalDateTime startedAt,
        LocalDateTime endedAt
) {
    public static GameRes from(Game game) {
        return new GameRes(
                game.getGameId(),
                game.getGroupId(),
                game.getTotalRounds(),
                game.getTimer(),
                game.isInProgress(),
                game.getStartedAt(),
                game.getEndedAt()
                );
    }
}
