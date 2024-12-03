package io.urdego.notification_service.api.service;

import io.urdego.notification_service.api.controller.dto.NotificationRequestInfo;

public interface NotificationService {
	public void publishNotification(NotificationRequestInfo requestInfo);
}
