package io.urdego.group_service.api.controller.group.dto.request;

public record UpdateGroupReq(
        Long groupId,
        String groupName,
        String description,
        Integer memberLimit,
        Long userId
) {
}
