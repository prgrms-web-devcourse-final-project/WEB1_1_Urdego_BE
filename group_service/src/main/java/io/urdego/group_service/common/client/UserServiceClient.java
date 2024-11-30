package io.urdego.group_service.common.client;

import io.urdego.group_service.api.controller.groupMember.dto.response.ResponseUserInfo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
    @GetMapping("/api/user-service/user/{userId}")
    ResponseUserInfo getUserById(@PathVariable("userId") Long userId);
}
