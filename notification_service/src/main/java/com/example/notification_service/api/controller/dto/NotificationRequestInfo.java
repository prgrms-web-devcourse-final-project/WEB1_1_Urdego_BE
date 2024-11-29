package com.example.notification_service.api.controller.dto;

import java.util.List;
import org.apache.logging.log4j.message.StringFormattedMessage;

public record NotificationRequestInfo(
		Long groupId,
		Long senderId,
		List<Long> targetIds
) {

}
