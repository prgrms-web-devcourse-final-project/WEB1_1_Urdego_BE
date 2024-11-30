package io.urdego.group_service.common.client;

import io.urdego.group_service.common.client.request.UserNicknameRequest;
import io.urdego.group_service.common.client.response.ResponseUserInfo;
import io.urdego.group_service.common.client.response.UserIdListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
    @GetMapping("/api/user-service/user/{userId}")
    ResponseUserInfo getUserById(@PathVariable("userId") Long userId);

    @PostMapping("/api/user-service/users/ids")
    UserIdListResponse mapNicknameToIdInBatch(@RequestBody UserNicknameRequest request);
}
