package urdego.api.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import urdego.api.user.controller.request.ContentUploadRequest;
import urdego.domain.entity.user.UserContent;
import urdego.domain.entity.user.constant.ContentInfo;
import urdego.domain.entity.user.repository.UserContentRepository;
import urdego.external.aws.service.S3Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserContentServiceImpl implements UserContentService {

    private final S3Service s3Service;
    private final UserContentRepository userContentRepository;

    public void uploadContent(ContentUploadRequest request, MultipartFile file) {

        String url = uploadFile(request.getUserId(), file);

        // URL을 JSON 문자열로 변환 (단일 값 처리)
        String urlJson = "\"" + url + "\"";

        ContentInfo contentInfo = contentMetadata(file);

        UserContent userContent = UserContent.builder()
                .userId(request.getUserId())
                .contentName(request.getContentName())
                .url(urlJson)
                .contentInfo(contentInfo)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .hint(request.getHint())
                .build();
        userContentRepository.save(userContent);
    }

    // 단일 파일 업로드 처리
    private String uploadFile(Long userId, MultipartFile file) {
        return s3Service.uploadSingleContent(userId, file);
    }

    // 컨텐츠 메타데이터 추출
    private ContentInfo contentMetadata(MultipartFile file) {
        return s3Service.extractMetadata(file);
    }
}
