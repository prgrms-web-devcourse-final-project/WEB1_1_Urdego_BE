package io.urdego.game_service.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    GAME_NOT_FOUND("해당 게임을 찾을 수 없습니다."),
    PLAYER_NOT_FOUND("해당 플레이어를 찾을 수 없습니다."),
    ROUND_NOT_FOUND("해당 라운드 정보를 찾을 수 없습니다."),
    GROUP_PLAYER_NOT_FOUND("해당 그룹에서 플레이 가능한 인원을 찾을 수 없습니다."),
    ROUND_CONTENT_NOT_FOUND("라운드 생성을 위한 컨텐츠를 불러올 수 없습니다.")
    ;

    private final String text;
}
