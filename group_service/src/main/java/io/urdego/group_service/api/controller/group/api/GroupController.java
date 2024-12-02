package io.urdego.group_service.api.controller.group.api;

import io.urdego.group_service.api.controller.group.api.dto.request.CreateGroupReq;
import io.urdego.group_service.api.controller.group.api.dto.request.UpdateGroupReq;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupCreateRes;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupInfoRes;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupListRes;
import io.urdego.group_service.api.controller.group.api.dto.response.GroupRes;
import io.urdego.group_service.api.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/group-service/groups")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    // 그룹 생성
    @PostMapping
    public ResponseEntity<GroupCreateRes> createGroup(@RequestBody CreateGroupReq request) {
        log.info("GroupController.createGroup");
        GroupCreateRes response = groupService.createGroup(request);
        return ResponseEntity.status(CREATED).body(response);
    }

    // 그룹 정보 수정
    @PutMapping("/{groupId}")
    public ResponseEntity<GroupRes> updateGroup(
            @PathVariable Long groupId, @RequestBody UpdateGroupReq request) {

        GroupRes response = groupService.updateGroup(groupId, request);
        return ResponseEntity.status(OK).body(response);
    }

    // 그룹 정보 조회 feign 연결 api
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupInfoRes> getGroupInfo(@PathVariable Long groupId) {
        GroupInfoRes response = groupService.getGroupInfo(groupId);
        return ResponseEntity.status(OK).body(response);
    }

    // 모든 그룹 리스트 조회
    @GetMapping
    public ResponseEntity<GroupListRes> getAllGroups() {
        GroupListRes response = groupService.getAllGroups();
        return ResponseEntity.status(OK).body(response);
    }

    // 그룹 비활성화
    @PatchMapping("/{groupId}/deactivate")
    public ResponseEntity<Void> deactivateGroup(@PathVariable Long groupId) {
        groupService.deactivateGroup(groupId);
        return ResponseEntity.status(OK).build();
    }

    // 그룹 활성화
    @PatchMapping("/{groupId}/activate")
    public ResponseEntity<Void> activateGroup(@PathVariable Long groupId) {
        groupService.activateGroup(groupId);
        return ResponseEntity.status(OK).build();
    }

    //그룹 웹 소켓 open

}