package io.urdego.group_service.infra.repository;

import io.urdego.group_service.infra.entity.group.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupJPARepository extends JpaRepository<GroupEntity, Long> {
}
