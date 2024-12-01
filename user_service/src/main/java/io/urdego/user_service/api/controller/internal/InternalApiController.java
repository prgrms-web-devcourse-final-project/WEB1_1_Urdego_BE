package io.urdego.user_service.api.controller.internal;

import io.urdego.user_service.api.controller.internal.request.UserRequest;
import io.urdego.user_service.api.controller.internal.response.UserIdListResponse;
import io.urdego.user_service.api.controller.internal.response.UserInfo;
import io.urdego.user_service.api.controller.internal.response.UserNicknameRequest;
import io.urdego.user_service.api.controller.internal.response.UserResponse;
import io.urdego.user_service.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-service")
public class InternalApiController {

    private final UserService userService;

    @GetMapping("/users/{email}")
    public ResponseEntity<UserInfo> getUser(@PathVariable("email") String email) {
        UserInfo response = userService.findUserByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") Long userId) {
        UserResponse response = userService.findUserById(userId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/usersByNickname")
    public ResponseEntity<UserInfo> getUserByNickname(@RequestBody UserRequest request) {
        UserInfo response = userService.findByOneNickname(request.nickname());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/users/validate")
    public ResponseEntity<List<Long>> validateUserIds(@RequestParam(name = "userIds") List<Long> userIds) {
        List<Long> userIdList = userService.validateUserIds(userIds);
        return ResponseEntity.ok().body(userIdList);
    }

    @PostMapping("/users/ids")
    public ResponseEntity<UserIdListResponse> mapNicknameToIdInBatch(@RequestBody UserNicknameRequest request) {
        List<Long> ids = userService.getIdByNicknameInBatch(request.nicknames());
        return ResponseEntity.ok(UserIdListResponse.of(ids));
    }

}
