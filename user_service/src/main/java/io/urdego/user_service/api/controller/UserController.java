package io.urdego.user_service.api.controller;

import io.urdego.user_service.api.controller.request.SignUpRequest;
import io.urdego.user_service.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-service")
public class UserController {
    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("success");
    }

    @PostMapping("/users")
    public ResponseEntity<Long> signUp(@RequestBody final SignUpRequest request){
        System.out.println("UserController.signUp");
        Long userId = userService.signUp(request);
        return ResponseEntity.ok(userId);
    }


}