package io.urdego.user_service.api.controller;

import io.urdego.user_service.api.controller.request.SignInRequest;
import io.urdego.user_service.api.controller.request.SignUpRequest;
import io.urdego.user_service.api.controller.request.VerifyNicknameRequest;
import io.urdego.user_service.api.service.UserService;
import io.urdego.user_service.api.service.NicknameVerificationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-service")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Long> postUser(@RequestBody final SignUpRequest request){
        Long userId = userService.signUp(request);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/nickname")
    public ResponseEntity<String> postNickname(@RequestBody final VerifyNicknameRequest request) {
        NicknameVerificationResult result = userService.verifyNickname(request.nickname());
        return ResponseEntity.ok(result.getStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody final SignInRequest request) {
        String nickname = userService.login(request.email(), request.password());
        return ResponseEntity.ok(nickname);
    }


}