package io.urdego.group_service.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    GROUP_NOT_FOUND("해당 그룹을 찾을 수 없습니다."),
    GROUP_MEMBER_NOT_FOUND("해당 멤버를 찾을 수 없습니다."),
    GROUP_MEMBER_LIMITED("그룹 제한 인원을 초과했습니다."),
    GROUP_MEMBER_ALREADY_EXISTS("이미 존재하는 그룹 멤버입니다."),
    NOT_MANAGER("그룹 관리자 권한이 없습니다."),
    NOT_FOUND_USER("해당 이메일을 사용하는 유저를 찾을 수 없습니다.");

    private final String text;
}
