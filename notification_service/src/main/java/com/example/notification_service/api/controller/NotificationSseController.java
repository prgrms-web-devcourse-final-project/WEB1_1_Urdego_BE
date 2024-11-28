package com.example.notification_service.api.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
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
	private final RedisMessageListenerContainer container;
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final RedisMessageListenerContainer redisMessageListenerContainer;

	//클라이언트 SSE 연결
	@GetMapping("/{userId}")
	public SseEmitter sseEmitter(@PathVariable Long userId) {
		//SSE 연결
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // 연결 시간 최대
		emitters.put(userId, emitter);

		//연결 종료
		emitter.onCompletion(() -> emitters.remove(userId));
		emitter.onTimeout(() -> emitters.remove(userId));

		//Redis 채널 구독 설정
		redisMessageListenerContainer.addMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message, byte[] pattern) {
				String channel = new String(pattern);
				String messageStr = message.toString();

				//SSE로 클라에 메세지 전송
				if(channel.equals("user:"+userId)) {
					try{
						emitter.send(SseEmitter.event().data(messageStr));
					}catch (IOException e){
						log.error("failed to send SSE to user {}: {}", userId, e.getMessage());
						emitters.remove(userId);
					}
				}
			}
		}, new PatternTopic("user:"+userId));
		return emitter;
	}
}
