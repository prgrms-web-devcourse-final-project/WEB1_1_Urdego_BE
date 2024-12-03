package io.urdego.game_service.common.client;

import io.urdego.game_service.common.client.dto.request.UserReq;
import io.urdego.game_service.common.client.dto.response.UserInfoRes;
import io.urdego.game_service.common.client.dto.response.UserRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
    @PostMapping("/api/user-service/usersByNickname")
    UserInfoRes getUserIdByNickname(@RequestBody UserReq request);

    @GetMapping("api/user-service/user/{userId}")
    UserRes getUserById(@PathVariable Long userId);
}
