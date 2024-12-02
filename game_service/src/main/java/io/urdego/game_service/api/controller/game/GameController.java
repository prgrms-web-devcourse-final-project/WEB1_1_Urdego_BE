package io.urdego.game_service.api.controller.game;

import io.urdego.game_service.api.controller.game.dto.response.GameRes;
import io.urdego.game_service.api.service.game.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game-service")
public class GameController {

    private final GameService gameService;

    // 게임 생성
    @PostMapping("/games/{gruopId}/start")
    public ResponseEntity<Long> createGame(@PathVariable Long groupId) {
        GameRes response = gameService.startGame(groupId);
        return ResponseEntity.ok(response.gameId());
    }
}
