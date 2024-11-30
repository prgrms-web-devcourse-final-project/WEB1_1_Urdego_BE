package io.urdego.game_service.api.controller.player.dto.response;

import io.urdego.game_service.domain.entity.player.Player;

public record PlayerRes(
        Long userId,
        int totalScore
) {
    public static PlayerRes from(Player player) {
        return new PlayerRes(
                player.getUserId(),
                player.getTotalScore()
        );
    }
}
