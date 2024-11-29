package io.urdego.group_service.api.controller.group.dto.request;

public record UpdateGroupReq(
        String groupName, String description, Integer memberLimit, Long userId) {}
