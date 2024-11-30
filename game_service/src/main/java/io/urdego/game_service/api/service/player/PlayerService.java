package io.urdego.game_service.api.service.player;

import io.urdego.game_service.api.controller.player.dto.response.PlayerRes;

import java.util.List;

public interface PlayerService {
    // 플레이어 정보 목록
    List<Long> getPlayerIds(Long groupId);

    // 플레이어 점수 업데이트
    void updatePlayerTotalScore(Long playerId, int score);

    // 플레이어 총점 조회
    List<PlayerRes> getPlayerTotalScore(Long gameId);
}
