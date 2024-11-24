package io.urdego.group_service.api.service.group;

import io.urdego.group_service.api.controller.group.dto.request.CreateGroupReq;
import io.urdego.group_service.api.controller.group.dto.request.UpdateGroupReq;
import io.urdego.group_service.api.controller.group.dto.response.GroupListRes;
import io.urdego.group_service.api.controller.group.dto.response.GroupRes;
import io.urdego.group_service.common.exception.ExceptionMessage;
import io.urdego.group_service.common.exception.group.GroupException;
import io.urdego.group_service.common.exception.groupMember.GroupMemberException;
import io.urdego.group_service.domain.entity.group.Group;
import io.urdego.group_service.domain.entity.group.repository.GroupRepository;
import io.urdego.group_service.domain.entity.groupMember.GroupMember;
import io.urdego.group_service.domain.entity.groupMember.GroupMemberRole;
import io.urdego.group_service.domain.entity.groupMember.repository.GroupMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    // 그룹 생성
    @Override
    public GroupRes createGroup(CreateGroupReq request) {
        Group group =
                Group.builder()
                        .groupName(request.groupName())
                        .description(request.description())
                        .memberLimit(request.memberLimit())
                        .userId(request.userId())
                        .build();

        group = groupRepository.save(group);

        // 생성자에게 MANAGER 권한 부여
        GroupMember manager =
                GroupMember.builder()
                        .groupId(group.getGroupId())
                        .userId(group.getUserId())
                        .memberRole(GroupMemberRole.MANAGER)
                        .build();
        groupMemberRepository.save(manager);

        log.info("Group created : {} by {}", group, request.userId());

        return GroupRes.from(group);
    }

    // 그룹 정보 수정
    @Override
    public GroupRes updateGroup(UpdateGroupReq request) {
        Group group = findByGroupIdOrThrowGroupException(request.groupId());

        // 수정 권한 검증
        groupMemberRepository
                .findByGroupIdAndUserId(group.getGroupId(), request.userId())
                .filter(member -> member.getMemberRole() == GroupMemberRole.MANAGER)
                .orElseThrow(
                        () -> {
                            log.warn(
                                    "UserId : {} : {}",
                                    request.userId(),
                                    ExceptionMessage.NOT_MANAGER);
                            return new GroupMemberException(ExceptionMessage.NOT_MANAGER.getText());
                        });

        group.update(request.groupName(), request.description(), request.memberLimit());
        group = groupRepository.save(group);
        log.info("Group updated : {}", group);

        return GroupRes.from(group);
    }

    // 그룹 정보 조회
    @Override
    @Transactional(readOnly = true)
    public GroupRes getGroupInfo(Long groupId) {
        Group group = findByGroupIdOrThrowGroupException(groupId);

        return GroupRes.from(group);
    }

    // 그룹 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public GroupListRes getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupRes> groupList = groups.stream().map(GroupRes::from).toList();

        log.info("Retrieved {} groups", groupList.size());
        return GroupListRes.from(groupList);
    }

    // 그룹 비활성화
    @Override
    public void deactivateGroup(Long groupId) {
        Group group = findByGroupIdOrThrowGroupException(groupId);

        group.deactivate();
        groupRepository.save(group);
        log.info("Group deactivated: {}", groupId);
    }

    // 그룹 활성화
    @Override
    public void activateGroup(Long groupId) {
        Group group = findByGroupIdOrThrowGroupException(groupId);

        group.activate();
        groupRepository.save(group);
        log.info("Group activated: {}", groupId);
    }

    // 공통 메서드
    private Group findByGroupIdOrThrowGroupException(Long groupId) {
        return groupRepository
                .findById(groupId)
                .orElseThrow(
                        () -> {
                            log.warn("GroupId {} : {}", groupId, ExceptionMessage.GROUP_NOT_FOUND);
                            return new GroupException(ExceptionMessage.GROUP_NOT_FOUND.getText());
                        });
    }
}
