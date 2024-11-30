package com.example.notification_service.api.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ssl.SslManagerBundle;
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
	private Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	@Override
	public SseEmitter connect(Long userId) {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // 최대
		emitters.put(userId,emitter);

		//SSE 연결 종료 시 클라이언트 제거
		emitter.onCompletion(() -> emitters.remove(userId));
		emitter.onTimeout(()-> emitters.remove(userId));

		// 채널 구독
		container.addMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message, byte[] pattern) {
				String channel = new String(pattern);
				String msgStr = new String(message.getBody());

				log.debug("Received message on channel {}: {}", channel, msgStr);

				if(channel.equals("user:" + userId)){
					try{
						emitter.send(SseEmitter.event().data(msgStr));
						log.debug("Sent SSE message to userId: {}", userId);
					} catch (IOException e){
						log.error("Failed to send SSE to user {}: {}", userId, e.getMessage());
						emitters.remove(userId);
					}
				}
			}
		}, new PatternTopic("user:" + userId));
		return emitter;
	}

	@Override
	public void disconnect(Long userId) {
		SseEmitter emitter = emitters.remove(userId);
		if(emitter != null){
			emitter.complete();
		}
	}
}
