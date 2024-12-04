package io.urdego.group_service.api.controller.group.websocket.response;

import java.util.List;

public record GameStartResponse(
        Long gameId,
        List<String> joinedUser,
        Integer totalRounds,
        String action
) {
    public static GameStartResponse of(Long gameId, List<String> joinedUser, Integer totalRounds) {
        return new GameStartResponse(gameId, joinedUser, totalRounds, "startGame");
    }
}
