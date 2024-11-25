package io.urdego.group_service.api.controller.groupMember;

import io.urdego.group_service.api.controller.groupMember.dto.request.AddGroupMemberReq;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberListRes;
import io.urdego.group_service.api.service.groupMember.GroupMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/group-service/{groupId}/members")
@RequiredArgsConstructor
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    // 그룹에 멤버 추가
    @PostMapping
    public ResponseEntity<Void> addMember(@PathVariable Long groupId, @RequestBody AddGroupMemberReq request) {
        groupMemberService.addMember(groupId, request);
        return ResponseEntity.status(OK).build();
    }

    // 그룹에서 멤버 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long groupId, @PathVariable Long userId) {
        groupMemberService.removeMember(groupId, userId);
        return ResponseEntity.status(OK).build();
    }

    // 그룹 멤버 리스트 반환
    @GetMapping
    public ResponseEntity<GroupMemberListRes> getMemberList(@PathVariable Long groupId) {
        GroupMemberListRes response = groupMemberService.getMemberList(groupId);
        return ResponseEntity.status(OK).body(response);
    }
}
