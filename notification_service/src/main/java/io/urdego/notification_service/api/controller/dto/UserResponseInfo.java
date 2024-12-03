package io.urdego.notification_service.api.controller.dto;

public record UserResponseInfo(
		Long userId,

		String email,

		String nickname
) {

}
