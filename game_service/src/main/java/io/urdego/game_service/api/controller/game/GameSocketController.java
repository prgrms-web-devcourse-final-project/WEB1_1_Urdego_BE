package io.urdego.game_service.api.controller.game;

import io.urdego.game_service.api.controller.game.dto.response.GameRes;
import io.urdego.game_service.api.controller.game.dto.response.GameStartRes;
import io.urdego.game_service.api.service.game.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameSocketController {

    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;

    // 게임 시작
    @MessageMapping("/game/start")
//    @SendTo("/game-service/subscribe/game/start")
    public void startGame(@Payload Long gameId) {
        log.info("Game start requested for groupId: {}", gameId);

        GameStartRes gameInfo = gameService.startGame(gameId);
        log.info("Game started with gameId: {}", gameInfo.gameId());

        String destination = "/game-service/subscribe/game/" + gameId + "/start";
        messagingTemplate.convertAndSend(destination, gameInfo);
    }

    // 게임 종료
    @MessageMapping("/game/{gameId}/end")
//    @SendTo("/game-service/subscribe/game/{gameId}/end")
    public void endGame(@DestinationVariable Long gameId) {
        log.info("Game end requested for gameId: {}", gameId);

        gameService.endGame(gameId);
        log.info("Game with gameId {} has ended", gameId);

        String destination = "/game-service/subscribe/game/" + gameId + "/end";
        messagingTemplate.convertAndSend(destination, "Game ended successfully.");
    }

    // 게임 정보 조회
    @MessageMapping("/game/{gameId}/info")
//    @SendTo("/game-service/subscribe/game/{gameId}/info")
    public void getGameInfo(@DestinationVariable Long gameId) {
        log.info("Fetching game info for gameId: {}", gameId);

        GameRes gameInfo = gameService.getGameInfo(gameId);
        log.info("Fetched game info: {}", gameInfo);

        String destination = "/game-service/subscribe/game/" + gameId + "/info";
        messagingTemplate.convertAndSend(destination, gameInfo);
    }
}
