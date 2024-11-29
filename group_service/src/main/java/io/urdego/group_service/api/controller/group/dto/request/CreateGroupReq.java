package io.urdego.group_service.api.controller.group.dto.request;

import java.util.List;

public record CreateGroupReq(
        Long userId,    //그룹 생성자 id
        String groupName,
        String description,
        Integer memberLimit,
        // Integer timer,
        Integer totalRounds, // 라운드 수
        List<String> invitedUserNicknames
) {
}

