package io.urdego.group_service.api.controller.group.websocket.response;

public record GameStartResponse(
        Long gameId,
        String action
) {
    public static GameStartResponse of(Long gameId) {
        return new GameStartResponse(gameId, "startGame");
    }
}
