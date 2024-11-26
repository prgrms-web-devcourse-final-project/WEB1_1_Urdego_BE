package io.urdego.user_service.api.controller.external.response;

import io.urdego.user_service.domain.entity.User;

public record UserInfoResponse(
        Long id,
        String nickname
) {
    public static UserInfoResponse from(final User user) {
        return new UserInfoResponse(user.getId(), user.getNickname());
    }
}
