package io.urdego.game_service.domain.entity.game.repository;

import io.urdego.game_service.domain.entity.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    // gameId와 groupId 매핑
    @Query("SELECT g.groupId FROM Game g WHERE g.gameId = :gameId")
    Long findGroupIdByGameId(@Param("gameId") Long gameId);
}
