package io.urdego.game_service.api.controller.round;

import io.urdego.game_service.api.controller.round.dto.request.RoundCreateReq;
import io.urdego.game_service.api.controller.round.dto.response.RoundRes;
import io.urdego.game_service.api.service.round.RoundService;
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
public class RoundSocketController {

    private final RoundService roundService;
    private final SimpMessagingTemplate messagingTemplate;

    // 라운드 생성
    @MessageMapping("/rounds/create")
//    @SendTo("/game-service/subscribe/rounds/create")
    public void createRound(@Payload RoundCreateReq request) {
        log.info("Create round for gameId: {}", request.gameId());

        try {
            // 라운드 생성 또는 기존 라운드 반환
            RoundRes roundRes = roundService.createRound(request);

            // 브로드캐스트
            String destination = "/game-service/subscribe/game/" + request.gameId() + "/rounds/create";
            messagingTemplate.convertAndSend(destination, roundRes);

        } catch (Exception e) {
            log.error("라운드 생성 중 오류가 발생했습니다. gameId: {}, roundNum: {}, error: {}", request.gameId(), request.roundNum(), e.getMessage(), e);
        }
    }

    // 라운드 종료
    @MessageMapping("/rounds/{roundId}/end")
//    @SendTo("/game-service/subscribe/rounds/{roundId}/end")
    public void endRound(@DestinationVariable int roundNum, @Payload Long gameId) {
        log.info("Ending round: {}", roundNum);

        String response = roundNum + "라운드가 종료되었습니다!";

        String destination = "/game-service/subscribe/game/" + gameId + "/rounds/" + roundNum + "/end";
        messagingTemplate.convertAndSend(destination, response);
    }
}
