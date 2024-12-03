package io.urdego.game_service.common.client;

import io.urdego.game_service.common.client.dto.response.ContentRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CONTENT-SERVICE")
public interface ContentServiceClient {
    @GetMapping("/api/content-service/game/{contentId}")
    ContentRes getContent(@PathVariable Long contentId);

    @PostMapping("/api/content-service/game/contents")
    List<ContentRes> getUserContents(@RequestBody List<Long> userIds);
}
