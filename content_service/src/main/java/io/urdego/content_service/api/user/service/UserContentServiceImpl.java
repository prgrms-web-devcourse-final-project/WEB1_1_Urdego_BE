package io.urdego.content_service.api.user.service;

import io.urdego.content_service.api.user.controller.external.request.ContentUploadRequest;
import io.urdego.content_service.api.user.controller.external.response.UserContentListAndCursorIdxResponse;
import io.urdego.content_service.api.user.controller.external.response.UserContentResponse;
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

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserContentServiceImpl implements UserContentService {

    private final S3Service s3Service;
    private final UserContentRepository userContentRepository;
    private static final Long MAX_LIMIT = 10L;

    // 단일 컨텐츠 등록
    @Override
    public void uploadContent(ContentUploadRequest request, MultipartFile file) {

        String url = uploadFile(request.getUserId(), file);

        ContentInfo contentInfo = contentMetadata(file);

        UserContent userContent =
                UserContent.builder()
                        .userId(request.getUserId())
                        .contentName(request.getContentName())
                        .address(request.getAddress())
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
                            .address(request.getAddress())
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

    // 유저 컨텐츠 조회
    @Override
    public UserContentListAndCursorIdxResponse getUserContents(
            Long userId, Long cursorIdx, Long limit) {

        limit = Math.min(limit, MAX_LIMIT);

        List<UserContentResponse> userContents = userContentRepository.findUserContentsByUserId_CursorPaging(userId, cursorIdx, limit);

        // 컨텐츠가 비어있을경우 빈 배열 반환
        if (userContents.isEmpty()) {

            return UserContentListAndCursorIdxResponse.builder()
                    .userContents(Collections.emptyList())
                    .userId(userId)
                    .build();
        }

        UserContentListAndCursorIdxResponse response =
                UserContentListAndCursorIdxResponse.builder()
                        .userContents(userContents)
                        .userId(userId)
                        .build();
        response.setNextCursorIdx();

        return response;
    }

    // 게임 컨텐츠 조회
    @Override
    public List<UserContentResponse> getContents(List<Long> userIds) {

        // 컨텐츠 조회 (userIds 필터링)
        List<UserContentResponse> allContents = userContentRepository.findRandomContentsByUserIds(userIds);

        // 데이터가 3개미만 예외처리
        if (allContents.size() < 3) {
            throw new UserContentException(ExceptionMessage.GAME_CONTENT_NOT_ENOUGH);
        }

        // 위/경도를 기준으로 그룹화
        Map<String, List<UserContentResponse>> groupedByLocation = allContents.stream()
                .collect(Collectors.groupingBy(content -> {
                    Double latitude = content.getLatitude();
                    Double longitude = content.getLongitude();
                    return (latitude != null && longitude != null) ? latitude + "," + longitude : "null";
                }));

        // 그룹을 리스트로 변환 후 셔플
        List<Map.Entry<String, List<UserContentResponse>>> shuffledGroups = new ArrayList<>(groupedByLocation.entrySet());
        Collections.shuffle(shuffledGroups);

        List<UserContentResponse> result = new ArrayList<>();

        // 라운드 진행을 위한 처리
        int maxRounds = 3;
        int round = 0;

        // 셔플된 그룹 리스트를 순회하며 라운드에 추가
        for (Map.Entry<String, List<UserContentResponse>> entry : shuffledGroups) {
            if (round >= maxRounds) break;

            List<UserContentResponse> group = entry.getValue();

            // 그룹에서 랜덤으로 최대 3개 선택
            Collections.shuffle(group);
            List<UserContentResponse> selected = group.stream().limit(3).toList();

            result.addAll(selected);

            round++;
        }

        // 컨텐츠 최소 3개에서 최대 9개
        return result.stream()
                .limit(Math.max(3, Math.min(result.size(), 9)))
                .collect(Collectors.toList());
    }

    // 컨텐츠 단일 조회
    @Override
    public UserContentResponse getContent(Long contentId) {

        // 컨텐츠 조회
        UserContent userContent = findUserContentByIdOrException(contentId);

        return UserContentResponse.of(userContent);
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
        return userContentRepository
                .findById(contentId).orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", contentId, ExceptionMessage.USER_CONTENT_NOT_FOUND);
                    throw new UserContentException(ExceptionMessage.USER_CONTENT_NOT_FOUND);
                });
    }
}
