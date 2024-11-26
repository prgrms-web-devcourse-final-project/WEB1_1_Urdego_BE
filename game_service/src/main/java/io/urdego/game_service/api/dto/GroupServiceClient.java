package io.urdego.game_service.api.dto;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GroupService")
public interface GroupServiceClient {}
