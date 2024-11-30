package io.urdego.game_service.domain.entity.submission.repository;

import io.urdego.game_service.domain.entity.submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
