package io.urdego.notification_service.api.controller;

import io.urdego.notification_service.api.service.SseService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/notification-service/sse")
public class NotificationSseController {
	private final SseService sseService;
	//SSE 연결
	@ApiResponse(responseCode = "200", description = "sse 연결 성공")
	@GetMapping("/connect/{email}")
	public SseEmitter connect(@PathVariable("email") String email) {
		return sseService.connect(email);
	}

	//SSE 연결 끊기
	@DeleteMapping("/disconnect/{userId}")
	public void disconnect(@PathVariable("userId") Long userId) {
		sseService.disconnect(userId);
	}
}
