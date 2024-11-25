package io.urdego.user_service.api.controller.response;

import io.urdego.user_service.domain.define.User;

public record UserInfo(
		Long id,
		String email
) {
	public static UserInfo convertToUserInfo(final User user){
		return new UserInfo(user.getId(), user.getEmail());
	}
}
