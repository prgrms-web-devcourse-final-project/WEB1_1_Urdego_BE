package io.urdego.game_service.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    GAME_NOT_FOUND("해당 게임을 찾을 수 없습니다."),
    ROUND_NOT_FOUND("해당 라운드 정보를 찾을 수 없습니다."),
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다."),
    ROUND_CONTENT_NOT_FOUND("라운드 생성을 위한 컨텐츠를 불러올 수 없습니다."),
    SUBMISSION_NOT_FOUND("제출된 답안이 없습니다.")
    ;

    private final String text;
}
