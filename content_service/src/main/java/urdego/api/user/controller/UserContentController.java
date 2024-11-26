package urdego.api.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import urdego.api.user.controller.request.ContentUploadRequest;
import urdego.api.user.service.UserContentService;

@RestController
@RequestMapping("/api/content-service")
@RequiredArgsConstructor
public class UserContentController {

    private final UserContentService userContentService;


    // 컨텐츠 단일 등록
    /*
      TODO: security 도입후 수정
     */
    @PostMapping(value = "/contents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadContent(@RequestParam("file") MultipartFile file,
                                              @RequestParam("userId") Long userId,
                                              @RequestParam("contentName") String contentName,
                                              @RequestParam("latitude") Double latitude,
                                              @RequestParam("longitude") Double longitude,
                                              @RequestParam("hint") String hint) {

        ContentUploadRequest request = ContentUploadRequest.builder()
                .userId(userId)
                .contentName(contentName)
                .latitude(latitude)
                .longitude(longitude)
                .hint(hint)
                .build();
        userContentService.uploadContent(request, file);

        return ResponseEntity.ok().build();
    }
}