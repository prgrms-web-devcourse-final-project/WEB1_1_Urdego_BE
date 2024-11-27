package io.urdego.game_service.domain.entity.player.repository;

import io.urdego.game_service.domain.entity.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByGameId(Long gameId);
}
