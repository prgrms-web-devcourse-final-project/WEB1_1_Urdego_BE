package io.urdego.game_service.common.client.dto.response;

import java.util.List;

public record GroupInfoRes(
        Long groupId,
        int totalRounds,
        int timer,
        List<Long> invitedUserIds,
        String groupName,
        Integer memberLimit,
        boolean isDeleted
) {
}
