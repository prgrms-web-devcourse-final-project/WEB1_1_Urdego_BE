package io.urdego.group_service.api.controller.group.dto.response;

import java.util.List;

public record GroupListRes(
        List<GroupRes> groups
) {
    public static GroupListRes from(List<GroupRes> groupResList) {
        return new GroupListRes(groupResList);
    }
}
