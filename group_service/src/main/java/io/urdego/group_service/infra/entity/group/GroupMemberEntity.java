package io.urdego.group_service.infra.entity.group;

import io.urdego.group_service.infra.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "GroupMember")
public class GroupMemberEntity extends BaseEntity {

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
}
