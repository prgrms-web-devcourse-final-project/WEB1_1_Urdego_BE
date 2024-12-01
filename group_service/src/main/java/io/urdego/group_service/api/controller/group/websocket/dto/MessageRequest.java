package io.urdego.group_service.api.controller.group.websocket.dto;

public record MessageRequest(
        EventType eventType,
        String nickname,
        Long gameId
        ) {
}
