package com.example.notification_service.api.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationPublisher {
	private final StringRedisTemplate redisTemplate;

	public void publishToUser(List<Long> targetIds, Long senderId, Long groupId){
		for(Long targetId : targetIds){
			//알림 메세지 생성
			String message = groupId + "에서 " + senderId + "님이 " + targetId + "님을 게임에 초대했습니다.";

			//알림 ID 생성
			String notificationId = UUID.randomUUID().toString();

			//Pub/Sub 채널
			String channel = "user:" + targetId;

			//Redis 메세지 발행
			redisTemplate.convertAndSend(channel, message);

			log.info("from : " + senderId + "to : " + targetId + "message : " + message);
		}
	}
}
