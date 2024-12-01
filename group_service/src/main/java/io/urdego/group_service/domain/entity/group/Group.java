package io.urdego.group_service.domain.entity.group;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import io.urdego.group_service.domain.entity.BaseEntity;

import jakarta.persistence.*;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity(name = "`Groups`")
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

    @Column(name = "total_rounds", nullable = false)
    private Integer totalRounds;

    private Integer timer; // 60초 고정

    // 그룹 상태 처리 (for soft delete)
    @Column(nullable = false)
    private boolean isDeleted = false;

    // FK
    @Column(name = "user_id", nullable = false)
    private Long userId;

    //현재 참여한 그룹멤버에게 부여할 그룹참가번호
    //1부터 시작, 번호 부여 후 ++
    private Integer gaveParticipantNumber;

    @Builder
    public Group(String groupName, String description, Integer memberLimit, Integer totalRounds, Long userId) {
        this.groupName = groupName;
        this.description = description;
        this.memberLimit = memberLimit;
        this.totalRounds = totalRounds;
        this.userId = userId;
        this.isDeleted = false;
        this.timer = 60; // 60초 고정
        this.gaveParticipantNumber = 1;
    }

    // 그룹 정보 수정 메서드
    public void update(String groupName, String description, Integer memberLimit) {
        this.groupName = groupName;
        this.description = description;
        this.memberLimit = memberLimit;
    }

    // 상태 변경 메서드
    public void deactivate() {
        this.isDeleted = true;
    }

    public void activate() {
        this.isDeleted = false;
    }

    public Integer addMember() {
        return gaveParticipantNumber++;
    }
}
