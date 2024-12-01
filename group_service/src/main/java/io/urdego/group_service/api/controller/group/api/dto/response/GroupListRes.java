package io.urdego.group_service.api.controller.group.api.dto.response;

import java.util.List;

public record GroupListRes(List<GroupRes> groups) {
    public static GroupListRes from(List<GroupRes> groupList) {
        return new GroupListRes(groupList);
    }
}
