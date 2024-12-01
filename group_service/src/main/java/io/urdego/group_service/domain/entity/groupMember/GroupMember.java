package io.urdego.group_service.domain.entity.groupMember;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import io.urdego.group_service.domain.entity.group.Group;
import jakarta.persistence.*;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity(name = "GroupMembers")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long groupMemberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private GroupMemberRole memberRole;

    @Column(name = "member_status", nullable = false)
    private String memberStatus;

    // FK
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    //대기방 내 입장 순서
    private Integer participantNumber;

    @Builder
    public GroupMember(Group group, Long userId, GroupMemberRole memberRole) {
        this.groupId = group.getGroupId();
        this.userId = userId;
        this.memberRole = memberRole;
        memberStatus = "notReady";
        this.participantNumber = group.addMember();
    }
    public void ready() {
        this.memberStatus = "Ready";
    }
}
