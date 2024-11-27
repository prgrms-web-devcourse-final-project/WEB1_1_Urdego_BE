package io.urdego.content_service.api.user.service;

import io.urdego.content_service.api.user.controller.request.ContentUploadRequest;
import io.urdego.content_service.common.exception.ExceptionMessage;
import io.urdego.content_service.common.exception.aws.AwsException;
import io.urdego.content_service.common.exception.user.UserContentException;
import io.urdego.content_service.domain.entity.user.UserContent;
import io.urdego.content_service.domain.entity.user.constant.ContentInfo;
import io.urdego.content_service.domain.entity.user.repository.UserContentRepository;
import io.urdego.content_service.external.aws.service.S3Service;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserContentServiceImpl implements UserContentService {

    private final S3Service s3Service;
    private final UserContentRepository userContentRepository;

    // 단일 컨텐츠 등록
    @Override
    public void uploadContent(ContentUploadRequest request, MultipartFile file) {

        String url = uploadFile(request.getUserId(), file);

        ContentInfo contentInfo = contentMetadata(file);

        UserContent userContent =
                UserContent.builder()
                        .userId(request.getUserId())
                        .contentName(request.getContentName())
                        .url(url)
                        .contentInfo(contentInfo)
                        .latitude(request.getLatitude())
                        .longitude(request.getLongitude())
                        .hint(request.getHint())
                        .build();
        userContentRepository.save(userContent);
    }

    // 다중 컨텐츠 등록
    @Override
    public void uploadContentMultiple(ContentUploadRequest request, MultipartFile[] files) {
        for (MultipartFile file : files) {

            String url = uploadFile(request.getUserId(), file);

            ContentInfo contentInfo = contentMetadata(file);

            // UserContent 생성
            UserContent userContent =
                    UserContent.builder()
                            .userId(request.getUserId())
                            .url(url)
                            .contentInfo(contentInfo)
                            .contentName(request.getContentName())
                            .latitude(request.getLatitude())
                            .longitude(request.getLongitude())
                            .hint(request.getHint())
                            .build();
            userContentRepository.save(userContent);
        }
    }

    // 컨텐츠 단일 삭제
    @Override
    public void deleteContent(Long contentId) {

        UserContent userContent = findUserContentByIdOrException(contentId);

        String filename = s3Service.extractFileNameFromUrl(userContent.getUrl());

        try {
            // S3에서 파일 삭제
            s3Service.deleteFile(filename);

            // RDB에서 데이터 삭제
            userContentRepository.deleteById(userContent.getId());
        } catch (Exception e) {

            throw new AwsException(ExceptionMessage.CONTENT_DELETE_FAILED);
        }
    }

    // 단일 파일 업로드 처리
    private String uploadFile(Long userId, MultipartFile file) {
        return s3Service.uploadSingleContent(userId, file);
    }

    // 컨텐츠 메타데이터 추출
    private ContentInfo contentMetadata(MultipartFile file) {
        return s3Service.extractMetadata(file);
    }

    // 컨텐츠 조회
    private UserContent findUserContentByIdOrException(Long contentId) {
        return userContentRepository.findById(contentId).orElseThrow(() -> {
            log.warn(">>>> {} : {} <<<<", contentId, ExceptionMessage.USER_CONTENT_NOT_FOUND);
            throw new UserContentException(ExceptionMessage.USER_CONTENT_NOT_FOUND);
        });
    }
}
