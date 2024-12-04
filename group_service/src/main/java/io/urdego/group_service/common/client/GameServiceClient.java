package io.urdego.group_service.common.client;

import io.urdego.group_service.api.controller.group.api.dto.request.GroupCreateReq;
import io.urdego.group_service.common.client.request.GroupInfoReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GAME-SERVICE")
public interface GameServiceClient {

    @PostMapping("/api/game-service/games")
    Long createGame(@RequestBody GroupCreateReq groupInfoReq);
}
