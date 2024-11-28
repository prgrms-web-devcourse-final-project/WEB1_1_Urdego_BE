package io.urdego.game_service.common.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GroupService")
public interface GroupServiceClient {}
