package io.urdego.group_service.common.client.request;

import lombok.Builder;

import java.util.List;

public record GroupInfoReq(

        Long groupId,
        int totalRounds,
        int timer,
        int playerCounts,
        List<Long> invitedUsers
) {

    @Builder
    public GroupInfoReq(Long groupId, int totalRounds, int timer, int playerCounts, List<Long> invitedUsers) {
        this.groupId = groupId;
        this.totalRounds = totalRounds;
        this.timer = timer;
        this.playerCounts = playerCounts;
        this.invitedUsers = invitedUsers;
    }
}
