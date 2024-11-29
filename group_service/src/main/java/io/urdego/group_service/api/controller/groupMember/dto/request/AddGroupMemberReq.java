package io.urdego.group_service.api.controller.groupMember.dto.request;

import io.urdego.group_service.domain.entity.groupMember.GroupMemberRole;

public record AddGroupMemberReq(Long userId,
                                GroupMemberRole role) {}
