package io.urdego.content_service.common.client;

import io.urdego.content_service.common.client.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "USER-SERVICE", url = "https://USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/api/user-service/user/{userId}")
    UserResponse getUserById(@PathVariable("userId") Long userId);


    @GetMapping("/api/user-service/users/validate")
    List<Long> validateUserIds(@RequestParam List<Long> userIds);
}
