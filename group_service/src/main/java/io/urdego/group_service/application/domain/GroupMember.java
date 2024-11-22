package io.urdego.group_service.application.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GroupMember {

    private Long groupMemberId;
    private Long groupId;
    private Long userId;
    private String memberRole;
    private String memberStatus;

    // 생성자
    private GroupMember(Long groupMemberId, Long groupId, Long userId, String memberRole, String memberStatus) {
        this.groupMemberId = groupMemberId;
        this.groupId = groupId;
        this.userId = userId;
        this.memberRole = memberRole;
        this.memberStatus = memberStatus;
    }
}
