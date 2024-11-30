package io.urdego.game_service.api.controller.round;

import io.urdego.game_service.api.controller.round.dto.request.RoundCreateReq;
import io.urdego.game_service.api.controller.round.dto.response.RoundRes;
import io.urdego.game_service.api.service.round.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game-service/rounds")
@RequiredArgsConstructor
public class RoundController {

    private final RoundService roundService;

    // 라운드 생성
    @PostMapping
    public ResponseEntity<RoundRes> createRound(@RequestBody RoundCreateReq request) {
        RoundRes response = roundService.createRound(request);
        return ResponseEntity.ok(response);
    }
}
