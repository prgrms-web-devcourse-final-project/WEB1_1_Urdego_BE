package io.urdego.content_service.api.user.service;

import io.urdego.content_service.api.user.controller.external.request.ContentUploadRequest;
import io.urdego.content_service.api.user.controller.external.response.UserContentListAndCursorIdxResponse;
import io.urdego.content_service.api.user.controller.external.response.UserContentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserContentService {

    // 단일 컨텐츠 등록
    void uploadContent(ContentUploadRequest request, MultipartFile file);

    // 다증 컨텐츠 등록
    void uploadContentMultiple(ContentUploadRequest request, MultipartFile[] files);

    // 컨텐츠 삭제
    void deleteContent(Long contentId);

    // 컨텐츠 조회
    UserContentListAndCursorIdxResponse getUserContents(Long userId, Long cursorIdx, Long limit);

    // 게임 컨텐츠 조회
    List<UserContentResponse> getContents(List<Long> userIds);

    // 컨텐츠 단일 조회
    UserContentResponse getContent(Long contentId);
}
