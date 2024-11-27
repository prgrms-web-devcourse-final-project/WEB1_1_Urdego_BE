package io.urdego.game_service.domain.entity.submission.repository;

import io.urdego.game_service.domain.entity.submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByRoundId(Long roundId);
    List<Submission> findByPlayerId(Long playerId);
}
