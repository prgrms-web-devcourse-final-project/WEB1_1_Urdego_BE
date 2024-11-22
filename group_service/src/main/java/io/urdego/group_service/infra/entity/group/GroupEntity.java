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
@Table(name = "Group")
public class GroupEntity extends BaseEntity {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(name = "group_name")
    private String groupName;

    private String description;

    @Column(name = "maximum", nullable = false)
    private int memberMax;

    private String status;

    // FK
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
