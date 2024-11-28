package com.example.notification_service.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "notification")
@Builder
public class Notification {
	@Id
	private String id;

	private Long groupId;

	private Long senderId;

	private Long targetId;

	private boolean isAccepted;

	private boolean isFinished; //isAccepted가 false면

	private String message = groupId + "에서 " + senderId +"가" + targetId + "를 초대하고 싶어합니다.";

	private LocalDateTime timestamp;
}
