package io.urdego.group_service.api.controller.group.websocket.request;

import io.urdego.group_service.api.controller.group.websocket.EventType;

import java.util.Map;

public record MessageRequest(
        EventType eventType,
        Map<String, Object> data
        ) {
}
