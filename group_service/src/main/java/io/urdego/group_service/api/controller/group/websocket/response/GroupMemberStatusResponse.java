package io.urdego.group_service.api.controller.group.websocket.response;

import io.urdego.group_service.domain.entity.groupMember.GroupMember;
import lombok.Data;

@Data
public class GroupMemberStatusResponse {
    String nickname;
    String status;
    //participantNumber
    Integer id;

    private GroupMemberStatusResponse(String nickname, String status, Integer id) {
        this.nickname = nickname;
        this.status = status;
        this.id = id;
    }

    public static GroupMemberStatusResponse from(GroupMember groupMember, String nickname) {
        return new GroupMemberStatusResponse(
                nickname,
                groupMember.getMemberStatus(),
                groupMember.getParticipantNumber());
    }
}
