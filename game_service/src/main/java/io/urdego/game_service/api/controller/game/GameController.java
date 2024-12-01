package io.urdego.game_service.api.controller.game;

import io.urdego.game_service.api.controller.game.dto.response.GameRes;
import io.urdego.game_service.api.service.game.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game-service")
public class GameController {

    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;

//    // 게임 시작
//    @PostMapping("/games/{gruopId}/start")
//    public ResponseEntity<Long> startGame(@PathVariable Long groupId) {
//        GameRes response = gameService.startGame(groupId);
//        return ResponseEntity.ok(response.gameId());
//    }

    // 게임 시작 (WS)
    @MessageMapping("/game/start")
    public void startGame(Long groupId) {
        GameRes response = gameService.startGame(groupId);

        messagingTemplate.convertAndSend("/topic/game/start",
                Map.of(
                        "gameId", response.gameId()
                ));
    }

    // 게임 정보 조회
    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameRes> getGameInfo(@PathVariable Long gameId) {
        GameRes response = gameService.getGameInfo(gameId);
        return ResponseEntity.ok(response);
    }

    // 게임 종료
    @PostMapping("/games/{gameId}/end")
    public ResponseEntity<Void> endGame(@PathVariable Long gameId) {
        gameService.endGame(gameId);
        return ResponseEntity.ok().build();
    }
}
