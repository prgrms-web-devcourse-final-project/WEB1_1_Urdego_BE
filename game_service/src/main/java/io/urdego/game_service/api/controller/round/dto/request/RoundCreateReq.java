package io.urdego.game_service.api.controller.round.dto.request;

import java.util.List;

public record RoundCreateReq(
        int roundNum,
        Long gameId,
        List<Long> playerIds
) {
}
