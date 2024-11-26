package io.urdego.user_service.api.controller.internal.response;

import io.urdego.user_service.domain.entity.User;

public record UserInfo(Long userId, String email, String nickname) {
    public static UserInfo convertToUserInfo(final User user) {
        return new UserInfo(user.getId(), user.getEmail(), user.getNickname());
    }
}
