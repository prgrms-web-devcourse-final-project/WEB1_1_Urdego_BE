package io.urdego.content_service.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    // UserContent
    USER_CONTENT_NOT_FOUND("존재하지 않는 유저 콘텐츠입니다."),

    // AWS
    INVALID_FILE_FORMAT("잘못된 형식의 파일입니다."),
    FILE_UPLOAD_FAILED("파일 업로드에 실패했습니다."),

    // Image
    IMAGE_METADATA_FAILED("이미지 메타데이터 추출에 실패했습니다.");

    private final String text;
}
