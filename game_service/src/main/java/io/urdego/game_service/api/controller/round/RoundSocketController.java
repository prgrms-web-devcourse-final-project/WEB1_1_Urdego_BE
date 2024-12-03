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
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RoundSocketController {

    private final RoundService roundService;

    // 라운드 생성
    @MessageMapping("/rounds/create")
    @SendTo("/game-service/subscribe/rounds/create")
    public RoundRes createRound(@Payload RoundCreateReq request) {
        log.info("Create round for gameId: {}", request.gameId());
        return roundService.createRound(request);
    }

    // 라운드 종료
    @MessageMapping("/rounds/{roundId}/end")
    @SendTo("/game-service/subscribe/rounds/{roundId}/end")
    public String endRound(@DestinationVariable int roundNum) {
        log.info("Ending round: {}", roundNum);
        return roundNum + "라운드가 종료되었습니다!";
    }
}
