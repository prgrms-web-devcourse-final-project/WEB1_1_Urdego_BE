package io.urdego.group_service.common.client.request;

import java.util.List;

public record NotificationRequestInfo(

        Long groupId,
        Long senderId,
        List<Long> targetIds
) {
    public static NotificationRequestInfo of(Long groupId, Long senderId, List<Long> targetIds) {
        return new NotificationRequestInfo(groupId, senderId, targetIds);
    }
}
