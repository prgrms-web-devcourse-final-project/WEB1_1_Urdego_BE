package io.urdego.group_service.infra.repository;

import io.urdego.group_service.application.port.out.GroupRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    private final GroupRepository groupRepository;



}
