package io.urdego.group_service.api.service.group;

import io.urdego.group_service.api.controller.group.api.dto.request.CreateGroupReq;
import io.urdego.group_service.api.controller.group.api.dto.request.UpdateGroupReq;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupCreateRes;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupInfoRes;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupListRes;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupRes;
import io.urdego.group_service.common.client.GameServiceClient;
import io.urdego.group_service.common.client.NotificationServiceClient;
import io.urdego.group_service.common.client.UserServiceClient;
import io.urdego.group_service.common.client.request.NotificationRequestInfo;
import io.urdego.group_service.common.client.request.UserNicknameRequest;
import io.urdego.group_service.common.client.response.ResponseUserInfo;
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
    private final UserServiceClient userServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final GameServiceClient gameServiceClient;

    // 그룹 생성
    @Override
    public GroupCreateRes createGroup(CreateGroupReq request) {

        Long roomManagerId = request.userId();

        //그룹 생성
        Group group = groupRepository.save(
                Group.builder()
                .groupName(request.groupName())
                .description(request.description())
                .memberLimit(request.memberLimit())
                .userId(roomManagerId)
                .totalRounds(request.totalRounds())
                .build());

        //초대된 유저들의 닉네임을 id로 매핑
        List<Long> ids = userServiceClient.mapNicknameToIdInBatch(
                UserNicknameRequest.of(
                        request.invitedUserNicknames()
                )
        ).userIds();

        //방장 id를 닉네임으로 매핑
        ResponseUserInfo roomManagerInfo = userServiceClient.getUserById(roomManagerId);

        // 초대된 유저들의 id로 초대 알림 발송
        String sendLog = notificationServiceClient.sendNotification(
                NotificationRequestInfo.of(
                        group.getGroupId(),
                        group.getGroupName(),
                        roomManagerId,  //sender 닉네임
                        roomManagerInfo.nickname(),
                        ids,
                        request.invitedUserNicknames()));  //초대된사람들 닉네임


        // 게임 서비스에 게임 생성 요청 _게임 서비스의 게임생성 API 미구현
        Long gameId = 0L;
//        Long gameId = gameServiceClient.createGame(
//                GroupInfoReq.builder().
//                        groupId(group.getGroupId()).
//                        totalRounds(request.totalRounds()).
//                        timer(group.getTimer()).
//                        playerCounts(request.memberLimit()).
//                        invitedUsers(ids).build());

        return GroupCreateRes.of(group.getGroupId(), gameId);
    }

    // 그룹 정보 수정
    @Override
    public GroupRes updateGroup(Long groupId, UpdateGroupReq request) {
        Group group = findByGroupIdOrThrowGroupException(groupId);

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
    public GroupInfoRes getGroupInfo(Long groupId) {
        Group group = findByGroupIdOrThrowGroupException(groupId);
        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);
        return GroupInfoRes.of(group, members);
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
