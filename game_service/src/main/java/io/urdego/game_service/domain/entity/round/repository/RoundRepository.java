package io.urdego.game_service.domain.entity.round.repository;

import io.urdego.game_service.domain.entity.round.Round;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findByGameId(Long gameId);
}
