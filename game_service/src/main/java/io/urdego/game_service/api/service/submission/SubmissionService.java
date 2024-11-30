package io.urdego.game_service.api.service.submission;

import io.urdego.game_service.api.controller.submission.dto.request.SubmissionReq;
import io.urdego.game_service.api.controller.submission.dto.response.SubmissionRes;

public interface SubmissionService {
    // 답안 제출
    SubmissionRes submitAnswer(SubmissionReq request);

    // 거리 계산
    double calculateDistance(double lat1, double lon1, double lat2, double lon2);

    // 거리 기반 점수 계산
    int calculateScore(double distance);
}
