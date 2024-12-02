package io.urdego.group_service.api.service.group;

import io.urdego.group_service.api.controller.group.api.dto.request.CreateGroupReq;
import io.urdego.group_service.api.controller.group.api.dto.request.UpdateGroupReq;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupCreateRes;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupInfoRes;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupListRes;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupRes;

public interface GroupService {
    // 그룹 생성
    GroupCreateRes createGroup(CreateGroupReq request);

    // 그룹 정보 수정
    GroupRes updateGroup(Long groupId, UpdateGroupReq request);

    // 그룹 정보 조회
    GroupInfoRes getGroupInfo(Long groupId);

    // 그룹 리스트 조회
    GroupListRes getAllGroups();

    // 그룹 비활성화
    void deactivateGroup(Long groupId);

    // 그룹 활성화
    void activateGroup(Long groupId);
}