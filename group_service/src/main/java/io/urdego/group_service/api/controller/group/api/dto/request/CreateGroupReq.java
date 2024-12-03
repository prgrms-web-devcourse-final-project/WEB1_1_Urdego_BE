package io.urdego.group_service.api.controller.group.api.dto.request;

import java.util.List;

public record CreateGroupReq(
        String title,   //groupName
        Integer maxPlayers, //memberLimit
        Integer rounds,   //totalRounds
        Integer timer,
        String host,    //방장 닉네임    _userId 대신
        List<String> invitedFriends
) {
}

