package io.urdego.group_service.api.controller.group;

import io.urdego.group_service.api.controller.group.dto.request.CreateGroupReq;
import io.urdego.group_service.api.controller.group.dto.request.UpdateGroupReq;
import io.urdego.group_service.api.controller.group.dto.response.GroupListRes;
import io.urdego.group_service.api.controller.group.dto.response.GroupRes;
import io.urdego.group_service.api.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/group-service")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    // 그룹 생성
    @PostMapping
    public ResponseEntity<GroupRes> createGroup(@RequestBody CreateGroupReq request) {
        GroupRes response = groupService.createGroup(request);
        return ResponseEntity.status(CREATED).body(response);
    }

    // 그룹 정보 수정
    @PutMapping("/{groupId}")
    public ResponseEntity<GroupRes> updateGroup(@PathVariable Long groupId, @RequestBody UpdateGroupReq request) {
        // 요청 객체에 그룹 ID 설정
        request = new UpdateGroupReq(groupId, request.groupName(), request.description(), request.memberLimit(), request.userId());
        GroupRes response = groupService.updateGroup(request);
        return ResponseEntity.status(OK).body(response);
    }

    // 그룹 정보 조회
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupRes> getGroupInfo(@PathVariable Long groupId) {
        GroupRes response = groupService.getGroupInfo(groupId);
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
}
