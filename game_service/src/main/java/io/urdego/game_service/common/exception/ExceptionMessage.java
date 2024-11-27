package io.urdego.game_service.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    GAME_NOT_FOUND("해당 게임을 찾을 수 없습니다."),
    PLAYER_NOT_FOUND("해당 플레이어를 찾을 수 없습니다.");

    private final String text;
}
