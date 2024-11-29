package io.urdego.group_service.api.controller.group.dto.request;

public record CreateGroupReq(
        String groupName,
        String description,
        Integer memberLimit,
        Long userId,
        //Integer timer,
        Integer totalRounds // 라운드 수
){

}
