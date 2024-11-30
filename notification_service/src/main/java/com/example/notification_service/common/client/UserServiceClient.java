package com.example.notification_service.common.client;

import com.example.notification_service.api.controller.dto.UserResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="USER-SERVICE")
public interface UserServiceClient {
	@GetMapping("/api/user-service/user/{userId}")
	 UserResponseInfo getUser(@PathVariable("userId") Long userId);
}
