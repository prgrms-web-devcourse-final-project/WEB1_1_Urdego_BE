package io.urdego.content_service.common.client;

import io.urdego.content_service.common.client.response.UserResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/api/user-service/user/{userId}")
    UserResponse getUserById(@PathVariable("userId") Long userId);
}
