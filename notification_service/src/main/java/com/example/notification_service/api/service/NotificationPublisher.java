package com.example.notification_service.api.service;

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
	private final UserServiceClient userServiceClient;
	private final GroupServiceClient groupServiceClient;
	private final ObjectMapper objectMapper;

	public void publishToUser(List<Long> targetIds, Long senderId, Long groupId){
		//Id들 nickName 및 groupTitle로 전환
		String senderNickName = userServiceClient.getUser(senderId).nickname();
		log.info("senderNickName : {}",senderNickName);
		String groupName = groupServiceClient.getGroupInfo(groupId).groupName();
		log.info("groupName : {}",groupName);
		List<String> targetNickNames = new ArrayList<>();
		for(int i = 0; i< targetIds.size(); i++){
			targetNickNames.add(userServiceClient.getUser(targetIds.get(i)).nickname());
			log.info("targetNickNames : {}",targetNickNames.get(i));

			//알림 메세지 생성
			NotificationMessage notification = NotificationMessage.of(
					UUID.randomUUID().toString(),
					groupId,
					groupName,
					senderId,
					senderNickName,
					targetIds.get(i),
					targetNickNames.get(i),
					"게임에 초대했습니다.",
					LocalDateTime.now()
			);

			String message;
			try {
				message = objectMapper.writeValueAsString(notification);
			}catch (JsonProcessingException e){
				log.error("failed to serialize Notification for userId: {}",targetIds.get(i));
				continue;
			}

			//Pub/Sub 채널
			String channel = "user:" + targetIds.get(i);

			//Redis 메세지 발행
			redisTemplate.convertAndSend(channel, message);

			log.info("from : " + senderId + "to : " + targetIds.get(i) + "message : " + message);
		}
	}
}
