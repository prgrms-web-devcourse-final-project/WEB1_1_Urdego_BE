package io.urdego.group_service.api.controller.group.dto.response;

import io.urdego.group_service.domain.entity.group.Group;

public record GroupRes(
        Long groupId,
        String groupName,
        String description,
        Integer memberLimit,
        boolean isDeleted,
        Long userId
) {
    public static GroupRes from(Group group) {
        return new GroupRes(
                group.getGroupId(),
                group.getGroupName(),
                group.getDescription(),
                group.getMemberLimit(),
                group.isDeleted(),
                group.getUserId()
        );
    }
}
