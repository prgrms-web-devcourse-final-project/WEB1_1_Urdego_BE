package io.urdego.group_service.domain.entity.group;

import io.urdego.group_service.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Group")
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
}
