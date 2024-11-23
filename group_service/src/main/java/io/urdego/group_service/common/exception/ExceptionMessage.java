package io.urdego.group_service.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    GROUP_NOT_FOUND("해당 그룹을 찾을 수 없습니다."),
    GROUP_MEMBER_NOT_FOUND("해당 멤버를 찾을 수 없습니다.")

    ;
    private final String text;
}
