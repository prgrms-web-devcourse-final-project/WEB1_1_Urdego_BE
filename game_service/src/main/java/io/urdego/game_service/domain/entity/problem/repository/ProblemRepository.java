package io.urdego.game_service.domain.entity.problem.repository;

import io.urdego.game_service.domain.entity.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByRoundId(Long roundId);
}
