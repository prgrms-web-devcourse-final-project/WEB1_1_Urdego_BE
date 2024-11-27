package io.urdego.game_service.domain.entity.game.repository;

import io.urdego.game_service.domain.entity.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByGroupId(Long groupId);
}
