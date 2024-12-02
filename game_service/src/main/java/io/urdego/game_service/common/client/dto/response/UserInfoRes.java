package io.urdego.game_service.common.client.dto.response;

public record UserInfoRes(
        Long userId,
        String email,
        String nickname
) {
}