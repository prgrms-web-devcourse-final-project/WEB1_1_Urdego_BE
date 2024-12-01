package io.urdego.group_service.common.client.response;

public record UserInfo(
        Long userId, String email, String nickname
) {
}
