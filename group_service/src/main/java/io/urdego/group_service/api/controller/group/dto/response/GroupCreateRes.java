package io.urdego.group_service.api.controller.group.dto.response;

public record GroupCreateRes(
        Long groupId,
        Long gameId
) {

    public static GroupCreateRes of(Long groupId, Long gameId) {
        return new GroupCreateRes(groupId, gameId);
    }
}
