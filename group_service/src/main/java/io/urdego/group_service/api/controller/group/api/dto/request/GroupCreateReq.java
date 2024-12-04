package io.urdego.group_service.api.controller.group.api.dto.request;

import lombok.Builder;

public record GroupCreateReq(
        Long groupId,
        int totalRounds,
        int timer,
        String groupName,
        Integer memberLimit,
        boolean isDeleted
) {
    @Builder
    public GroupCreateReq(Long groupId, int totalRounds, int timer, String groupName, Integer memberLimit, boolean isDeleted) {
        this.groupId = groupId;
        this.totalRounds = totalRounds;
        this.timer = timer;
        this.groupName = groupName;
        this.memberLimit = memberLimit;
        this.isDeleted = isDeleted;
    }
}
