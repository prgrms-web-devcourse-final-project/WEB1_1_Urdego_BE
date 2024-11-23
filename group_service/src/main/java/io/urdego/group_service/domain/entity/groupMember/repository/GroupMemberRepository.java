package io.urdego.group_service.domain.entity.groupMember.repository;

import io.urdego.group_service.domain.entity.groupMember.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
