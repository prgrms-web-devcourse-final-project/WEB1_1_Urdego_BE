package io.urdego.content_service.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    // UserContent
    USER_CONTENT_NOT_FOUND("존재하지 않는 유저 콘텐츠입니다.");

    private final String text;
}
