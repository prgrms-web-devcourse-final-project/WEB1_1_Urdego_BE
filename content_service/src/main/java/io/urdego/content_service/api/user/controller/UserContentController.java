package io.urdego.content_service.api.user.controller;

import io.urdego.content_service.api.user.controller.request.ContentUploadRequest;
import io.urdego.content_service.api.user.service.UserContentService;
import io.urdego.content_service.common.client.UserServiceClient;
import io.urdego.content_service.common.client.response.UserResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/content-service")
@RequiredArgsConstructor
public class UserContentController {

    private final UserContentService userContentService;
    private final UserServiceClient userServiceClient;

    // 컨텐츠 단일 등록
    /*
     TODO: security 도입후 수정
    */
    @PostMapping(value = "/contents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadContent(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("contentName") String contentName,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("hint") String hint) {

        // Feign 유저 검증
        UserResponse userResponse = userServiceClient.getUserById(userId);

        ContentUploadRequest request =
                ContentUploadRequest.builder()
                        .userId(userResponse.getUserId())
                        .contentName(contentName)
                        .latitude(latitude)
                        .longitude(longitude)
                        .hint(hint)
                        .build();
        userContentService.uploadContent(request, file);

        return ResponseEntity.ok().build();
    }

    // 여러 컨텐츠 등록
    @PostMapping(value = "/contents/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadMultipleContents(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("userId") Long userId,
            @RequestParam("contentName") String contentName,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("hint") String hint) {

        // Feign 유저 검증
        UserResponse userResponse = userServiceClient.getUserById(userId);

        // 각 파일에 대해 처리
        ContentUploadRequest request =
                ContentUploadRequest.builder()
                        .userId(userResponse.getUserId())
                        .contentName(contentName)
                        .latitude(latitude)
                        .longitude(longitude)
                        .hint(hint)
                        .build();
        userContentService.uploadContentMultiple(request, files);

        return ResponseEntity.ok().build();
    }
}
