package io.urdego.group_service.api.controller.group.websocket.dto;

public record MessageResponse<T>(
        T data
) {
}
