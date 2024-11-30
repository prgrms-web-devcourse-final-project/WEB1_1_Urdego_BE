package io.urdego.content_service.api.user.controller.external;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.urdego.content_service.api.user.controller.external.request.ContentUploadRequest;
import io.urdego.content_service.api.user.controller.external.response.UserContentListAndCursorIdxResponse;
import io.urdego.content_service.api.user.service.UserContentService;
import io.urdego.content_service.common.client.UserServiceClient;
import io.urdego.content_service.common.client.response.UserResponse;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @ApiResponse(responseCode = "200", description = "컨텐츠 업로드 성공")
    @PostMapping(value = "/contents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadContent(@RequestParam("file") MultipartFile file,
                                              @RequestParam("userId") Long userId,
                                              @RequestParam("contentName") String contentName,
                                              @RequestParam("address") String address,
                                              @RequestParam("latitude") Double latitude,
                                              @RequestParam("longitude") Double longitude,
                                              @RequestParam("hint") String hint) {

        // Feign 유저 검증
        UserResponse userResponse = userServiceClient.getUserById(userId);

        ContentUploadRequest request =
                ContentUploadRequest.builder()
                        .userId(userResponse.getUserId())
                        .contentName(contentName)
                        .address(address)
                        .latitude(latitude)
                        .longitude(longitude)
                        .hint(hint)
                        .build();
        userContentService.uploadContent(request, file);

        return ResponseEntity.ok().build();
    }

    // 여러 컨텐츠 등록
    @ApiResponse(responseCode = "200", description = "다중 컨텐츠 업로드 성공")
    @PostMapping(value = "/contents/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadMultipleContents(@RequestParam("files") MultipartFile[] files,
                                                       @RequestParam("userId") Long userId,
                                                       @RequestParam("contentName") String contentName,
                                                       @RequestParam("address") String address,
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
                        .address(address)
                        .latitude(latitude)
                        .longitude(longitude)
                        .hint(hint)
                        .build();
        userContentService.uploadContentMultiple(request, files);

        return ResponseEntity.ok().build();
    }

    // 컨텐츠 단일 삭제
    @ApiResponse(responseCode = "200", description = "컨텐츠 삭제 성공")
    @DeleteMapping(value = "{userId}/contents/{contentId}")
    public ResponseEntity<Void> deleteContent(@PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "contentId") Long contentId) {

        // Feign 유저 검증
        userServiceClient.getUserById(userId);

        userContentService.deleteContent(contentId);
        return ResponseEntity.ok().build();
    }

    // 컨텐츠 조회
    @ApiResponse(responseCode = "200", description = "유저 컨텐츠 조회 성공", content = @Content(schema = @Schema(implementation = UserContentListAndCursorIdxResponse.class)))
    @GetMapping(value = "{userId}/contents")
    public ResponseEntity<UserContentListAndCursorIdxResponse> getUserContents(@PathVariable(name = "userId") Long userId,
                                                                               @Min(value = 0) @RequestParam(name = "cursorIdx", required = false) Long cursorIdx,
                                                                               @Min(value = 1) @RequestParam(name = "limit", defaultValue = "5") Long limit) {

        // Feign 유저 검증
        UserResponse userResponse = userServiceClient.getUserById(userId);

        UserContentListAndCursorIdxResponse responses =
                userContentService.getUserContents(userResponse.getUserId(), cursorIdx, limit);

        return ResponseEntity.ok().body(responses);
    }
}
