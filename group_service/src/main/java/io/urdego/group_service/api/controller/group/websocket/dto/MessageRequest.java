package io.urdego.group_service.api.controller.group.websocket.dto;

import java.util.Map;

public record MessageRequest(
        EventType eventType,
        Map<String, Object> data
        ) {
}
