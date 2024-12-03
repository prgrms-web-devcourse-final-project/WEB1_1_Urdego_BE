package io.urdego.notification_service.api.controller.dto;

import java.util.List;
import org.apache.logging.log4j.message.StringFormattedMessage;

public record NotificationRequestInfo(
		Long groupId,
		String groupName,
		Long senderId,
		String senderNickname,
		List<Long> targetIds,
		List<String> targetNicknames
) {

}
