package io.urdego.user_service.api.controller.internal;

import io.urdego.user_service.api.controller.internal.response.UserInfo;
import io.urdego.user_service.api.controller.internal.response.UserResponse;
import io.urdego.user_service.api.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
