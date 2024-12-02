package io.urdego.game_service.domain.entity.submission.repository;

import io.urdego.game_service.domain.entity.submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findAllByUserId(Long userId);

    Optional<Submission> findByUserIdAndRoundId(Long userId, Long roundId);
}
