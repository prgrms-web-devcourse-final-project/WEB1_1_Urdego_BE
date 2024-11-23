package io.urdego.group_service.domain.entity.group;

import io.urdego.group_service.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity(name = "Group")
public class Group extends BaseEntity {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(name = "group_name")
    private String groupName;

    private String description;

    @Column(name = "member_limit", nullable = false)
    private Integer memberLimit;

    // 그룹 상태 처리 (for soft delete)
    @Column(nullable = false)
    private boolean groupStatus = true;

    // FK
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public Group(String groupName, String description, Integer memberLimit, boolean groupStatus, Long userId) {
        this.groupName = groupName;
        this.description = description;
        this.memberLimit = memberLimit;
        this.groupStatus = groupStatus;
        this.userId = userId;
    }
}
