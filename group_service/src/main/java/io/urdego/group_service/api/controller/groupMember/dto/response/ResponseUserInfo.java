package io.urdego.group_service.api.controller.groupMember.dto.response;

import java.util.Optional;

public record ResponseUserInfo(
		Long userId,
		String email,
		String nickname
) {

}
