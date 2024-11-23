package io.urdego.group_service.domain.entity.group.repository;

import io.urdego.group_service.domain.entity.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByGroupStatus(Boolean groupStatus);

}
