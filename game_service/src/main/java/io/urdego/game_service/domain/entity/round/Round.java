package io.urdego.game_service.domain.entity.round;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity(name = "Round")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roundId;

    @Column(nullable = false)
    private int roundNum;

    // FK
    @Column(nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private Long contentId;

    @Builder
    public Round(int roundNum, Long gameId, Long contentId) {
        this.roundNum = roundNum;
        this.gameId = gameId;
        this.contentId = contentId;
    }
}
