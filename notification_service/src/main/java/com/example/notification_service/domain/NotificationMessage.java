package com.example.notification_service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Component
public class NotificationMessage {
	private String notificationId;

	private Long groupId;
	private Long senderId;
	private Long targetId;

	private String groupName;
	private String senderNickName;
	private String targetNickName;

	private String action;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestamp;

	public static NotificationMessage of(String notificationId, Long groupId, String groupName,
			Long senderId, String senderNickName,
			Long targetId, String targetNickName,
			String action, LocalDateTime timestamp) {
		return NotificationMessage.builder()
				.notificationId(notificationId)
				.groupId(groupId)
				.groupName(groupName)
				.senderId(senderId)
				.senderNickName(senderNickName)
				.targetId(targetId)
				.targetNickName(targetNickName)
				.action(action)
				.timestamp(timestamp)
				.build();
	}
}
