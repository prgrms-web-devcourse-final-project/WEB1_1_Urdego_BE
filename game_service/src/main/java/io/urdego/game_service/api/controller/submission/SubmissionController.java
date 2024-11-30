package io.urdego.game_service.api.controller.submission;

import io.urdego.game_service.api.controller.submission.dto.request.SubmissionReq;
import io.urdego.game_service.api.controller.submission.dto.response.SubmissionRes;
import io.urdego.game_service.api.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game-service/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    // 정답 제출
    @PostMapping
    public ResponseEntity<SubmissionRes> submitAnswer(@RequestBody SubmissionReq request) {
        SubmissionRes response = submissionService.submitAnswer(request);
        return ResponseEntity.ok(response);
    }
}
