package io.urdego.game_service.api.controller.game;

import io.urdego.game_service.api.controller.game.dto.request.GameStartReq;
import io.urdego.game_service.api.controller.game.dto.response.GameRes;
import io.urdego.game_service.api.service.game.GameService;
import io.urdego.game_service.api.service.player.PlayerService;
import io.urdego.game_service.api.service.round.RoundService;
import io.urdego.game_service.api.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game-service/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    // 게임 시작
    @PostMapping("/start")
    public ResponseEntity<GameRes> startGame(@RequestBody GameStartReq request) {
        GameRes response = gameService.startGame(request);
        return ResponseEntity.ok(response);
    }

    // 게임 정보 조회
    @GetMapping("/{gameId}")
    public ResponseEntity<GameRes> getGameInfo(@PathVariable Long gameId) {
        GameRes response = gameService.getGameInfo(gameId);
        return ResponseEntity.ok(response);
    }

    // 게임 종료
    @PostMapping("/{gameId}/end")
    public ResponseEntity<Void> endGame(@PathVariable Long gameId) {
        gameService.endGame(gameId);
        return ResponseEntity.ok().build();
    }
}
