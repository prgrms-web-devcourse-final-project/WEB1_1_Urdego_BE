package io.urdego.group_service.api.controller.groupMember.dto.response;

import io.urdego.group_service.domain.entity.groupMember.GroupMember;
import io.urdego.group_service.domain.entity.groupMember.GroupMemberRole;

public record GroupMemberRes(
        Long groupId,
        Long userId,
        GroupMemberRole memberRole
) {
    public static GroupMemberRes from(GroupMember member) {
        return new GroupMemberRes(
                member.getGroupId(),
                member.getUserId(),
                member.getMemberRole()
        );
    }
}
