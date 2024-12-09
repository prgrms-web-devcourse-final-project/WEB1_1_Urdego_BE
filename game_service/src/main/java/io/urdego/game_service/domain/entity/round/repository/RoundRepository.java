package io.urdego.game_service.domain.entity.round.repository;

import io.urdego.game_service.domain.entity.round.Round;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Round r WHERE r.gameId = :gameId AND r.roundNum = :roundNum")
    Optional<Round> findByGameIdAndRoundNumForUpdate(@Param("gameId") Long gameId, @Param("roundNum") int roundNum);

    Optional<Round> findByGameIdAndRoundNum(Long gameId, int roundNum);
}
