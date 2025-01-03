package io.urdego.game_service.domain.entity.game.repository;

import io.urdego.game_service.domain.entity.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
