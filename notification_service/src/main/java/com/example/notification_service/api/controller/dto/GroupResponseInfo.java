package com.example.notification_service.api.controller.dto;

import java.util.List;

public record GroupResponseInfo(
		Long groupId,
		Integer totalRounds,
		Integer timer,
		int playerCounts,
		List<Long> invitedUserIds, // 방장 포함 초대된 유저들
		String groupName,
		String description,
		Integer memberLimit,
		boolean isDeleted
) {

}
