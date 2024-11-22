package io.urdego.group_service.infra.repository;

import io.urdego.group_service.infra.entity.group.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberJPARepository extends JpaRepository<GroupMemberEntity, Long> {
}
