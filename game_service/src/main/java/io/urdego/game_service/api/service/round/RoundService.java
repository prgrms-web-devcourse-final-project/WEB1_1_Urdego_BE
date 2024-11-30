package io.urdego.game_service.api.service.round;

import io.urdego.game_service.api.controller.round.dto.request.RoundCreateReq;
import io.urdego.game_service.api.controller.round.dto.response.RoundRes;

public interface RoundService {
    // 라운드 생성
    RoundRes createRound(RoundCreateReq request);
}
