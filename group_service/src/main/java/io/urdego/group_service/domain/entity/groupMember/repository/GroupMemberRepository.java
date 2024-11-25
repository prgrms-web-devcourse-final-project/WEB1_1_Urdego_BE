package io.urdego.group_service.domain.entity.groupMember.repository;

import io.urdego.group_service.domain.entity.groupMember.GroupMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    // 특정 그룹의 모든 멤버 조회
    List<GroupMember> findByGroupId(Long groupId);

    // 특정 그룹의 멤버 수 카운트
    long countByGroupId(Long groupId);

    // 특정 그룹에서 특정 유저가 멤버인지 확인
    Optional<GroupMember> findByGroupIdAndUserId(Long groupId, Long userId);

    // 특정 유저가 속한 그룹 조회
    List<GroupMember> findByUserId(Long userId);

    // 그룹 멤버의 상태 조회 (온/오프라인)
}
