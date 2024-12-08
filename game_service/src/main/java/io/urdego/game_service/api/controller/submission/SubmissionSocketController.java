package io.urdego.game_service.api.controller.submission;

import io.urdego.game_service.api.controller.submission.dto.request.SubmissionReq;
import io.urdego.game_service.api.controller.submission.dto.response.SubmissionRes;
import io.urdego.game_service.api.service.submission.SubmissionService;
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
public class SubmissionSocketController {

    private final SubmissionService submissionService;
    private final SimpMessagingTemplate messagingTemplate;

    // 답안 제출
    @MessageMapping("game/{gameId}/rounds/{roundId}/submit")
//    @SendTo("/game-service/subscribe/score")
    public void submitAnswer(
            @DestinationVariable Long gameId,
            @DestinationVariable Long roundId,
            @Payload SubmissionReq request) {
        log.info("답안이 제출되었습니다. gameId: {}, roundId: {}, nickname: {}", gameId, roundId, request.nickname());

        SubmissionRes submissionRes = submissionService.submitAnswer(request);
        log.info("제출 처리 결과: nickname: {}, result: {}", request.nickname(), submissionRes);

        String destination = "/game-service/subscribe/game/" + gameId + "/rounds/" + roundId + "/score";
        messagingTemplate.convertAndSend(destination, submissionRes);
    }
}
