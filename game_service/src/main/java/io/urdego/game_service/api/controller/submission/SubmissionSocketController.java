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
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SubmissionSocketController {

    private final SubmissionService submissionService;

    // 답안 제출
    @MessageMapping("game/{gameId}/rounds/{roundId}/submit")
    @SendTo("/game-service/subscribe/score")
    public SubmissionRes submitAnswer(
            @DestinationVariable Long gameId,
            @DestinationVariable Long roundId,
            @Payload SubmissionReq request) {

        log.info("Submission received for gameId: {}, roundId: {}, nickname: {}", gameId, roundId, request.nickname());

        SubmissionRes submissionRes = submissionService.submitAnswer(request);

        log.info("Processed submission for nickname: {}, result: {}", request.nickname(), submissionRes);

        return submissionRes;
    }
}
