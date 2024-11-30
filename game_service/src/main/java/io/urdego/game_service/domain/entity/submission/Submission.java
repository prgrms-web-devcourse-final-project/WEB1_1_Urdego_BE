package io.urdego.game_service.domain.entity.submission;

import io.urdego.game_service.domain.entity.game.Game;
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
@Entity(name = "Submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submissionId;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private int score;

    // FK
    @Column(nullable = false)
    private Long playerId;

    @Column(nullable = false)
    private Long roundId;

    @Builder
    public Submission(double latitude, double longitude, int score, Long playerId, Long roundId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
        this.playerId = playerId;
        this.roundId = roundId;
    }
}
