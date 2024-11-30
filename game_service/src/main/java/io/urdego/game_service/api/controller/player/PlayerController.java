package io.urdego.game_service.api.controller.player;

import io.urdego.game_service.api.controller.player.dto.response.PlayerRes;
import io.urdego.game_service.api.service.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game-service/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    // 플레이어 점수 조회
    @GetMapping("/{gameId}/scores")
    public ResponseEntity<List<PlayerRes>> getPlayerScores(@PathVariable Long gameId) {
        List<PlayerRes> response = playerService.getPlayerTotalScore(gameId);
        return ResponseEntity.ok(response);
    }
}
