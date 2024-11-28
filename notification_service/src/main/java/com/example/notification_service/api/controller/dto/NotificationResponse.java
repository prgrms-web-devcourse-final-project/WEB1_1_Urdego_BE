package com.example.notification_service.api.controller.dto;

import com.example.notification_service.domain.Notification;
import java.time.LocalDateTime;

public record NotificationResponse(
		String id,
		Long groupId,
		Long senderId,
		Long targetId,
		boolean isAccepted,
		String message,
		LocalDateTime timestamp
) {
	public static NotificationResponse fromNotification(Notification notification) {
		return new NotificationResponse(
				notification.getId(),
				notification.getGroupId(),
				notification.getSenderId(),
				notification.getTargetId(),
				notification.isAccepted(),
				notification.getMessage(),
				notification.getTimestamp()
		);
	}
}
