package io.urdego.game_service.domain.entity.submission.repository;

import io.urdego.game_service.api.controller.player.dto.response.PlayerRes;
import io.urdego.game_service.domain.entity.submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findAllByPlayerId(Long playerId);

    Optional<Submission> findByPlayerIdAndRoundId(Long userId, Long roundId);
}
