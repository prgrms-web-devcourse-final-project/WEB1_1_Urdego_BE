package io.urdego.user_service.api.controller.external;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.urdego.user_service.api.controller.external.request.SignInRequest;
import io.urdego.user_service.api.controller.external.request.SignUpRequest;
import io.urdego.user_service.api.controller.external.request.VerifyNicknameRequest;
import io.urdego.user_service.api.controller.external.response.UserInfoResponse;
import io.urdego.user_service.api.service.NicknameVerificationResult;
import io.urdego.user_service.api.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-service")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @ApiResponse(responseCode = "200", description = "응답 예시 : Hongildong12")
    public ResponseEntity<Long> postUser(@RequestBody final SignUpRequest request) {
        Long userId = userService.signUp(request);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/login")
    @ApiResponse(responseCode = "200", description = "응답 예시 : Hongildong12")
    public ResponseEntity<String> signIn(@RequestBody final SignInRequest request) {
        String nickname = userService.login(request.email(), request.password());
        return ResponseEntity.ok(nickname);
    }

    @PostMapping("/nickname")
    @ApiResponse(responseCode = "200", description = "응답 예시 : PERMIT / DUPLICATED")
    public ResponseEntity<String> postNickname(@RequestBody final VerifyNicknameRequest request) {
        NicknameVerificationResult result = userService.verifyNickname(request.nickname());
        return ResponseEntity.ok(result.getStatus());
    }

    @GetMapping("/nickname")
    public ResponseEntity<List<UserInfoResponse>> getUsersByString(final String string) {
        List<UserInfoResponse> userInfos = userService.findByNickname(string);
        return ResponseEntity.ok(userInfos);
    }
}
