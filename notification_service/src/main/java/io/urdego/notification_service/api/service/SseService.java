package io.urdego.notification_service.api.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
	void init();
	SseEmitter connect(String email);
	void disconnect(Long userId);
}
