package io.urdego.group_service.api.controller.group.api.dto.response;

import io.urdego.group_service.domain.entity.group.Group;

public record GroupRes(
        Long groupId,
        String groupName,
        String description,
        Integer memberLimit,
        Integer totalRounds,
        boolean isDeleted,
        Long userId) {
    public static GroupRes from(Group group) {
        return new GroupRes(
                group.getGroupId(),
                group.getGroupName(),
                group.getDescription(),
                group.getMemberLimit(),
                group.getTotalRounds(),
                group.isDeleted(),
                group.getUserId());
    }
}
