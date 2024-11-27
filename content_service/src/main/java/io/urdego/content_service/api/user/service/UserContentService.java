package io.urdego.content_service.api.user.service;

import io.urdego.content_service.api.user.controller.request.ContentUploadRequest;
import io.urdego.content_service.api.user.controller.response.UserContentListAndCursorIdxResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserContentService {

    // 단일 컨텐츠 등록
    void uploadContent(ContentUploadRequest request, MultipartFile file);

    // 다증 컨텐츠 등록
    void uploadContentMultiple(ContentUploadRequest request, MultipartFile[] files);

    // 컨텐츠 삭제
    void deleteContent(Long contentId);

    // 컨텐츠 조회
    UserContentListAndCursorIdxResponse getUserContents(Long userId, Long cursorIdx, Long limit);
}
