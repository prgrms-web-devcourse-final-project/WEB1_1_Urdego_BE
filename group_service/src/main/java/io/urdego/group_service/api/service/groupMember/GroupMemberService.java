package io.urdego.group_service.api.service.groupMember;

import io.urdego.group_service.api.controller.group.websocket.response.GroupMemberStatusResponse;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberListRes;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberRes;
import io.urdego.group_service.domain.entity.groupMember.GroupMemberRole;

import java.util.List;

public interface GroupMemberService {
    // 그룹 멤버 추가
    GroupMemberRes addMember(Long groupId, String nickname, GroupMemberRole role);

    // 그룹 멤버 제거
    void removeMember(Long groupId, Long userId);

    //    // 그룹 멤버 역할 변경
    //    void updateMemberRole(Long groupId, Long userId, String newRole);

    // 그룹 멤버 목록 조회
    GroupMemberListRes getMemberList(Long groupId);

    List<GroupMemberStatusResponse> getStatus(Long groupId);

    void ready(Long groupId, String nickname);
}
