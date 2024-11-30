package com.example.notification_service.api.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
	void init();
	SseEmitter connect(Long userId);
	void disconnect(Long userId);
}
