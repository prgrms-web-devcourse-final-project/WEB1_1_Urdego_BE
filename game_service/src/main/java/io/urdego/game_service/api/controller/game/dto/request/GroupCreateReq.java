package io.urdego.game_service.api.controller.game.dto.request;

public record GroupCreateReq(
        Long groupId,
        int totalRounds,
        int timer,
        String groupName,
        Integer memberLimit,
        boolean isDeleted
) {
}
