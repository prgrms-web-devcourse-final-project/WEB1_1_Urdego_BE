package io.urdego.group_service.api.controller.groupMember.dto.response;

import java.util.List;

public record GroupMemberListRes(List<GroupMemberRes> members) {
    public static GroupMemberListRes from(List<GroupMemberRes> groupMemberList) {
        return new GroupMemberListRes(groupMemberList);
    }
}
