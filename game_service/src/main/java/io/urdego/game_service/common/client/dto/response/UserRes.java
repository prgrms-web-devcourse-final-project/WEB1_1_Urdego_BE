package io.urdego.game_service.common.client.dto.response;

public record UserRes(
        Long userId,
        String email,
        String nickname
) {
}
