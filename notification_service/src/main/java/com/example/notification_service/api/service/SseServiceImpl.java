package com.example.notification_service.api.service;

import com.example.notification_service.api.controller.dto.UserResponseInfo;
import com.example.notification_service.common.client.UserServiceClient;
import com.example.notification_service.domain.NotificationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j
public class SseServiceImpl implements SseService {
	private final RedisMessageListenerContainer container;
	private final ObjectMapper objectMapper;
	private final UserServiceClient userServiceClient;

	private Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		// 단일 리스너로 모든 user:* 채널을 구독
		container.addMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message, byte[] pattern) {
				String channel = new String(message.getChannel());
				String msgStr = new String(message.getBody());

				log.debug("Received message on channel {}: {}", channel, msgStr);

				if (channel.startsWith("user:")) {
					Long userId;
					try {
						userId = Long.parseLong(channel.split(":")[1]);
					} catch (NumberFormatException e) {
						log.error("Invalid userId format in channel: {}", channel);
						return;
					}

					// JSON 메시지 파싱
					NotificationMessage notification;
					try {
						notification = objectMapper.readValue(msgStr, NotificationMessage.class);
					} catch (IOException e) {
						log.error("Failed to parse JSON message for userId: {}", userId, e);
						return;
					}

					// 해당 userId에 연결된 Emitter 가져오기
					SseEmitter emitter = emitters.get(userId);
					if (emitter != null) {
						try {
							emitter.send(SseEmitter.event()
									.name("notification")
									.data(notification));
							log.debug("Sent SSE message to userId: {}", userId);
						} catch (IOException e) {
							log.error("Failed to send SSE to user {}: {}", userId, e.getMessage());
							emitters.remove(userId);
						}
					} else {
						log.debug("No emitter found for userId: {}", userId);
					}
				}
			}
		}, new PatternTopic("user:*"));
	}

	@Override
	public SseEmitter connect(String email) {
		UserResponseInfo userInfo = userServiceClient.getUser(email);
		Long userId = userInfo.userId();
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // 연결 시간
		emitters.put(userId,emitter);

		//SSE 연결 종료 시 클라이언트 제거
		emitter.onCompletion(() -> {
			log.debug("SSE connection completed userId: {}", userId);
			emitters.remove(userId);
		});
		emitter.onTimeout(()-> {
			log.debug("SSE connection timed out userId: {}", userId);
			emitters.remove(userId);
		});

		return emitter;
	}

	@Override
	public void disconnect(Long userId) {
		log.debug("Disconnecting SSE for userId: {}", userId);
		SseEmitter emitter = emitters.remove(userId);
		if (emitter != null) {
			emitter.complete();
			log.debug("SSE connection completed for userId: {}", userId);
		}
	}
}
