package io.urdego.group_service.api.controller.group.dto.response;

import io.urdego.group_service.domain.entity.group.Group;
import io.urdego.group_service.domain.entity.groupMember.GroupMember;

import java.util.List;
import java.util.stream.Collectors;

public record GroupInfoRes(
        Long groupId,
        Integer totalRounds,
        Integer timer,
        int playerCounts,
        List<Long> invitedUserIds, // 방장 포함 초대된 유저들
        String groupName,
        String description,
        Integer memberLimit,
        boolean isDeleted) {
    public static GroupInfoRes of(Group group, List<GroupMember> invitedUsers) {
        return new GroupInfoRes(
                group.getGroupId(),
                group.getTotalRounds(),
                group.getTimer(),
                invitedUsers.size(),
                invitedUsers.stream().map(GroupMember::getUserId).collect(Collectors.toList()),
                group.getGroupName(),
                group.getDescription(),
                group.getMemberLimit(),
                group.isDeleted());
    }
}
