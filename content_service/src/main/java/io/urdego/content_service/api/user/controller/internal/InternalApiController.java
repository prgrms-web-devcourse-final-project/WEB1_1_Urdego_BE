package io.urdego.content_service.api.user.controller.internal;


import io.urdego.content_service.api.user.controller.external.response.UserContentResponse;
import io.urdego.content_service.api.user.service.UserContentService;
import io.urdego.content_service.common.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content-service")
public class InternalApiController {

    private final UserContentService userContentService;
    private final UserServiceClient userServiceClient;

    // 게임 컨텐츠 반환
    @PostMapping(value = "/game/contents")
    public ResponseEntity<List<UserContentResponse>> getContents(@RequestBody List<Long> userIds) {


       /* // Feign 유저 검증
        List<Long> userIdList = userServiceClient.validateUserIds(userIds);*/

        List<UserContentResponse> response = userContentService.getContents(userIds);

        return ResponseEntity.ok().body(response);
    }
}
