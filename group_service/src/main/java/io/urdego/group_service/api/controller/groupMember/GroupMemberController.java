package io.urdego.group_service.api.controller.groupMember;

import static org.springframework.http.HttpStatus.OK;

import io.urdego.group_service.api.controller.groupMember.dto.request.AddGroupMemberReq;
import io.urdego.group_service.api.controller.groupMember.dto.response.GroupMemberListRes;
import io.urdego.group_service.api.service.groupMember.GroupMemberService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group-service/{groupId}/members")
@RequiredArgsConstructor
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    // 그룹에 멤버 추가
    //@TODO 웹 소켓이 필요함
//    API가 아닌, 웹 소켓 메시지를 통해 groupMemberService 의 addMember 실행
//    @PostMapping
//    public ResponseEntity<Void> addMember(
//            @PathVariable Long groupId, @RequestBody AddGroupMemberReq request) {
//        groupMemberService.addMember(groupId, request);
//        return ResponseEntity.status(OK).build();
//    }

    // 그룹에서 멤버 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long groupId, @PathVariable Long userId) {
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
