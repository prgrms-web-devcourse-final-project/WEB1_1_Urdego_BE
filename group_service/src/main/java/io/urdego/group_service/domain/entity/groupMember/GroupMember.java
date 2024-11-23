package io.urdego.group_service.domain.entity.groupMember;

import jakarta.persistence.*;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity(name = "GroupMember")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long groupMemberId;

    @Column(name = "member_role", nullable = false)
    private GroupMemberRole memberRole;

    @Column(name = "member_status", nullable = false)
    private String memberStatus;

    // FK
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public GroupMember(GroupMemberRole memberRole, String memberStatus, Long groupId, Long userId) {
        this.memberRole = memberRole;
        this.memberStatus = memberStatus;
        this.groupId = groupId;
        this.userId = userId;
    }

}