package com.example.notification_service.api.service;

import com.example.notification_service.api.controller.dto.NotificationRequestInfo;
import com.example.notification_service.common.client.GroupServiceClient;
import com.example.notification_service.common.client.UserServiceClient;
import com.example.notification_service.domain.NotificationMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
	private final ObjectMapper objectMapper;
	private final UserServiceClient userServiceClient;
	private final GroupServiceClient groupServiceClient;

	public void publishToUser(NotificationRequestInfo requestInfo){
		//TODO feign 호출이 너무 많아서 배치 조회 알아보기
		/*//Id들 nickName 및 groupName로 전환
		String senderNickName = userServiceClient.getUser(senderId).nickname();
		log.info("senderNickName : {}",senderNickName);
		String groupName = groupServiceClient.getGroupInfo(groupId).groupName();
		log.info("groupName : {}",groupName);
		List<String> targetNickNames = new ArrayList<>();*/
		for(int i = 0; i< requestInfo.targetIds().size(); i++){
			//알림 메세지 생성
			NotificationMessage notification = NotificationMessage.of(
					UUID.randomUUID().toString(),
					requestInfo.groupId(),
					requestInfo.groupName(),
					requestInfo.senderId(),
					requestInfo.senderNickname(),
					requestInfo.targetIds().get(i),
					requestInfo.targetNicknames().get(i),
					"게임에 초대했습니다.",
					LocalDateTime.now()
			);

			String message;
			try {
				message = objectMapper.writeValueAsString(notification);
			}catch (JsonProcessingException e){
				log.error("failed to serialize Notification for userId: {}",requestInfo.targetIds().get(i));
				continue;
			}

			//Pub/Sub 채널
			String channel = "user:" + requestInfo.targetIds().get(i);

			//Redis 메세지 발행
			redisTemplate.convertAndSend(channel, message);

			log.info("from : " + requestInfo.senderId() + "to : " + requestInfo.targetIds().get(i) + "message : " + message);
		}
	}
}
