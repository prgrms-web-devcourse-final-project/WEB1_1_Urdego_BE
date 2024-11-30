package io.urdego.game_service.common.client;

import io.urdego.game_service.common.client.dto.response.GroupInfoRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "GROUP-SERVICE")
public interface GroupServiceClient {
    @GetMapping("/api/group-service/groups/{groupId}")
    GroupInfoRes getGroupInfo(@PathVariable Long groupId);
}
