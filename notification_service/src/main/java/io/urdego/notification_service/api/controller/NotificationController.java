package io.urdego.notification_service.api.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.urdego.notification_service.api.controller.dto.NotificationRequestInfo;
import io.urdego.notification_service.api.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification-service/notifications")
public class NotificationController {
	private final NotificationService notificationService;

	//알림 발행 및 보내기
	@ApiResponse(responseCode = "200", description = "알림 발송 성공")
	@PostMapping("/send")
	public String senNotification(@RequestBody NotificationRequestInfo requestInfo) {
		notificationService.publishNotification(requestInfo);
		return "Notifications sent to " + requestInfo.targetIds().size() + " users.";
	}
}
