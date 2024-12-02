package io.urdego.game_service.api.controller.round;

import io.urdego.game_service.api.controller.round.dto.request.RoundCreateReq;
import io.urdego.game_service.api.controller.round.dto.response.RoundRes;
import io.urdego.game_service.api.service.round.RoundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game-service")
public class RoundController {

    private final RoundService roundService;
    private final SimpMessagingTemplate messagingTemplate;

//    // 라운드 생성
//    @PostMapping("/rounds")
//    public ResponseEntity<RoundRes> createRound(@RequestBody RoundCreateReq request) {
//        RoundRes response = roundService.createRound(request);
//        return ResponseEntity.ok(response);
//    }

}
