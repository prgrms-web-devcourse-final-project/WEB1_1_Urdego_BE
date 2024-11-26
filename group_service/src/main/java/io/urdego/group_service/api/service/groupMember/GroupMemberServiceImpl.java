package io.urdego.group_service.api.service.groupMember;

import io.urdego.group_service.api.controller.groupMember.dto.request.AddGroupMemberReq;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberListRes;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberRes;
import io.urdego.group_service.api.controller.groupMember.dto.response.ResponseUserInfo;
import io.urdego.group_service.common.client.UserServiceClient;
import io.urdego.group_service.common.exception.ExceptionMessage;
import io.urdego.group_service.common.exception.group.GroupException;
import io.urdego.group_service.common.exception.groupMember.GroupMemberException;
import io.urdego.group_service.domain.entity.group.Group;
import io.urdego.group_service.domain.entity.group.repository.GroupRepository;
import io.urdego.group_service.domain.entity.groupMember.GroupMember;
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
public class GroupMemberServiceImpl implements GroupMemberService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserServiceClient userServiceClient;

    // 그룹에 멤버 추가
    @Override
    public GroupMemberRes addMember(Long groupId, AddGroupMemberReq request) {
        // 그룹 존재 여부 확인
        Group group =
                groupRepository
                        .findById(groupId)
                        .orElseThrow(
                                () -> {
                                    log.warn(
                                            "GroupId {} : {}",
                                            groupId,
                                            ExceptionMessage.GROUP_NOT_FOUND);
                                    return new GroupException(
                                            ExceptionMessage.GROUP_NOT_FOUND.getText());
                                });

        // 그룹 제한 인원 확인
        long currentMemberCount = groupMemberRepository.countByGroupId(group.getGroupId());
        if (currentMemberCount >= group.getMemberLimit()) {
            log.warn("GroupId {} : {}", group.getGroupId(), ExceptionMessage.GROUP_MEMBER_LIMITED);
            throw new GroupMemberException(ExceptionMessage.GROUP_MEMBER_LIMITED.getText());
        }

        // user-service에서 사용자 정보 가져오기
        ResponseUserInfo userInfo = userServiceClient.getUser(request.email());
        System.out.println("userInfo.email : " + userInfo.email());
        System.out.println("userInfo.userId : " + userInfo.userId());
        System.out.println("userInfo.nickname : " + userInfo.nickname());

        // 이미 그룹 멤버인지 확인
        groupMemberRepository
                .findByGroupIdAndUserId(groupId, request.userId())
                .ifPresent(
                        member -> {
                            log.warn(
                                    "UserId {}, GroupId {} : {}",
                                    request.userId(),
                                    groupId,
                                    ExceptionMessage.GROUP_MEMBER_ALREADY_EXISTS);
                            throw new GroupMemberException(
                                    ExceptionMessage.GROUP_MEMBER_ALREADY_EXISTS.getText());
                        });

        GroupMember groupMember =
                GroupMember.builder()
                        .groupId(group.getGroupId())
                        //.userId(request.userId())
                        .userId(userInfo.userId())
                        .memberRole(request.role())
                        .build();
        groupMember = groupMemberRepository.save(groupMember);

        log.info("UserId : {}, GroupId : {}, Role : {}", request.userId(), groupId, request.role());

        return GroupMemberRes.from(groupMember);
    }

    // 그룹 멤버 제거
    @Override
    public void removeMember(Long groupId, Long userId) {
        GroupMember member =
                groupMemberRepository
                        .findByGroupIdAndUserId(groupId, userId)
                        .orElseThrow(
                                () -> {
                                    log.warn(
                                            "UserId {}, GroupId {} : {}",
                                            userId,
                                            groupId,
                                            ExceptionMessage.GROUP_MEMBER_NOT_FOUND);
                                    return new GroupMemberException(
                                            ExceptionMessage.GROUP_MEMBER_NOT_FOUND.getText());
                                });

        groupMemberRepository.delete(member);
        log.info("Removed user {} from group {}", userId, groupId);
    }

    // 그룹 멤버 목록 조회
    @Override
    @Transactional(readOnly = true)
    public GroupMemberListRes getMemberList(Long groupId) {
        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);
        if (members.isEmpty()) {
            throw new GroupMemberException(ExceptionMessage.GROUP_MEMBER_NOT_FOUND.getText());
        }

        List<GroupMemberRes> memberList = members.stream().map(GroupMemberRes::from).toList();

        return GroupMemberListRes.from(memberList);
    }
}
