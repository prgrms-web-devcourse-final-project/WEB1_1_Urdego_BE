package com.example.notification_service.api.controller;

import com.example.notification_service.api.service.SseService;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
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
	//SSE 연결 프론트에서 email로 준다고 함
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
