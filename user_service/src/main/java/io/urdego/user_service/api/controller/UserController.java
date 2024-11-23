package io.urdego.user_service.api.controller;

import io.urdego.user_service.api.controller.request.SignUpRequest;
import io.urdego.user_service.api.service.UserService;
import io.urdego.user_service.api.service.constant.NicknameVerificationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-service")
public class UserController {
    private final UserService userService;

    //테스트
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("success");
    }

    @PostMapping("/users")
    public ResponseEntity<Long> postUser(@RequestBody final SignUpRequest request){
        Long userId = userService.signUp(request);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/nickname")
    public ResponseEntity<NicknameVerificationResult> postNickname(@RequestBody String nickname) {
        NicknameVerificationResult result = userService.verifyNickname(nickname);
        return ResponseEntity.ok(result);
    }

    

}