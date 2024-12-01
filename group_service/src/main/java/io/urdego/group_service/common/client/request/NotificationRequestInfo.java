package io.urdego.group_service.common.client.request;

import java.util.List;

public record NotificationRequestInfo(
        Long groupId,
        String groupName,
        Long senderId,
        String senderNickname,
        List<Long> targetIds,
        List<String> targetNicknames
) {
    public static NotificationRequestInfo of(Long groupId,
                                             String groupName,
                                             Long senderId,
                                             String senderNickname,
                                             List<Long> targetIds,
                                             List<String> targetNicknames) {
        return new NotificationRequestInfo(groupId, groupName, senderId, senderNickname, targetIds, targetNicknames);
    }
}
