package com.example.notification_service.common.client;

import com.example.notification_service.api.controller.dto.GroupResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "GROUP-SERVICE")
public interface GroupServiceClient {
	@GetMapping("api/group-service/groups/{groupId}")
	GroupResponseInfo getGroupInfo(@PathVariable Long groupId);

}
