package io.urdego.game_service.api.service.game;

import io.urdego.game_service.api.controller.game.dto.request.GroupCreateReq;
import io.urdego.game_service.api.controller.game.dto.response.GameRes;
import io.urdego.game_service.api.controller.game.dto.response.GameStartRes;
import io.urdego.game_service.common.client.dto.response.GroupInfoRes;

public interface GameService {
    // 게임 생성
    GameRes createGame(GroupCreateReq groupInfo);

    // 게임 시작
    GameStartRes startGame(Long gameId);

    // 게임 종료
    void endGame(Long gameId);

    // 게임 정보 조회
    GameRes getGameInfo(Long gameId);
}
