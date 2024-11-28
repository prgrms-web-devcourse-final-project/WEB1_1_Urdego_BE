package com.example.notification_service.api.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/notification-service/sse")
@RequiredArgsConstructor
public class NotificationSseController {
	private final StringRedisTemplate redisTemplate;

	@GetMapping
	public void streamNotifications(HttpServletResponse response) throws Exception {
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();

		redisTemplate.getConnectionFactory().getConnection().subscribe(new MessageListener() {
			@Override
			public void onMessage(Message message, byte[] pattern) {
				String notification = message.toString();
				writer.write("data:" + notification + "\n\n");
				writer.flush();
			}
		}, "user: *".getBytes());
	}
}
