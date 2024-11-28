package com.example.notification_service.api.controller;

import com.example.notification_service.api.controller.dto.NotificationRequestInfo;
import com.example.notification_service.api.service.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification-service/notifications")
public class NotificationController {
	private final NotificationPublisher notificationPublisher;

	//알림 발행 및 보내기
	@PostMapping("/send")
	public String senNotification(@RequestBody NotificationRequestInfo requestInfo) {
		notificationPublisher.publishToUser(requestInfo.targetIds(), requestInfo.senderId(), requestInfo.groupId());
		return "Notifications sent to " + requestInfo.targetIds().size() + " users.";
	}
}
