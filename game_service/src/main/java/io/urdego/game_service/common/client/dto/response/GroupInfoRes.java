package io.urdego.game_service.common.client.dto.response;

import java.util.List;

public record GroupInfoRes(
        Long groupId,
        int totalRounds,
        int timer,
        int playerCounts,
        List<Long> invitedUsers
) {
}
