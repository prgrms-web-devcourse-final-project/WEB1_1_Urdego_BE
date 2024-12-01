package io.urdego.group_service.api.service.groupMember;

import io.urdego.group_service.api.controller.group.websocket.dto.GroupMemberStatusResponse;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberListRes;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberRes;
import io.urdego.group_service.common.client.UserServiceClient;
import io.urdego.group_service.common.client.request.UserRequest;
import io.urdego.group_service.common.client.response.ResponseUserInfo;
import io.urdego.group_service.common.client.response.UserInfo;
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
public class GroupMemberServiceImpl implements GroupMemberService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserServiceClient userServiceClient;

    // 그룹에 멤버 추가
    @Override
    public GroupMemberRes addMember(Long groupId, String nickname, GroupMemberRole role) {
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

        //유저서비스에게 닉네임으로 유저 정보 호출
        System.out.println("GroupMemberServiceImpl.addMember 60");
        System.out.println("nickname = " + nickname);
        UserInfo userInfo = userServiceClient.getUserByNickname(UserRequest.of(nickname));
        System.out.println("GroupMemberServiceImpl.addMember 63");
        // 이미 그룹 멤버인지 확인
//        groupMemberRepository
//                .findByGroupIdAndUserId(groupId, userInfo.userId())
//                .ifPresent(
//                        member -> {
//                            log.warn(
//                                    "UserId {}, GroupId {} : {}",
//                                    userInfo.userId(),
//                                    groupId,
//                                    ExceptionMessage.GROUP_MEMBER_ALREADY_EXISTS);
//                            throw new GroupMemberException(
//                                    ExceptionMessage.GROUP_MEMBER_ALREADY_EXISTS.getText());
//                        });

        GroupMember saveGroupMember = groupMemberRepository.save(GroupMember.builder()
                .group(group)
                .userId(userInfo.userId())
                .memberRole(role)
                .build());

        return GroupMemberRes.from(saveGroupMember);
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

    @Override
    public List<GroupMemberStatusResponse> getStatus(Long groupId) {
        List<GroupMember> groupMembers = groupMemberRepository.findByGroupId(groupId);
        return groupMembers.stream().map(groupMember -> {
            ResponseUserInfo userInfo = userServiceClient.getUserById(groupMember.getUserId());
            return GroupMemberStatusResponse.from(groupMember, userInfo.nickname());
        }).toList();
    }
}
