package io.urdego.group_service.api.controller.group.websocket.response;

public record MessageResponse<T>(
        T data
) {
}
