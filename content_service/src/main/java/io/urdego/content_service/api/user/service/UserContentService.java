package io.urdego.content_service.api.user.service;

import org.springframework.web.multipart.MultipartFile;

import io.urdego.content_service.api.user.controller.request.ContentUploadRequest;

public interface UserContentService {

    // 단일 컨텐츠 등록
    void uploadContent(ContentUploadRequest request, MultipartFile file);
}
