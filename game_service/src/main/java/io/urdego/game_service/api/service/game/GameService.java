package io.urdego.game_service.api.service.game;

import io.urdego.game_service.api.controller.game.dto.request.GameStartReq;
import io.urdego.game_service.api.controller.game.dto.response.GameRes;

public interface GameService {
    // 게임 생성 및 시작
    GameRes startGame(GameStartReq request);

    // 게임 종료
    void endGame(Long gameId);

    // 게임 정보 조회
    GameRes getGameInfo(Long gameId);
}
