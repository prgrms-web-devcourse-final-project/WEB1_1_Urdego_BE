package io.urdego.game_service.domain.entity.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity(name = "Game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    @Column(nullable = false)
    private int totalRounds;

    @Column(nullable = false)
    private int timer;

    @Column(nullable = false)
    private int playerCounts;

    @Column(nullable = false)
    private boolean inProgress;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    // FK
    @Column(nullable = false)
    private Long groupId;

    @Builder
    public Game(int totalRounds, int timer, int playerCounts, boolean inProgress, Long groupId) {
        this.totalRounds = totalRounds;
        this.timer = timer;
        this.playerCounts = playerCounts;
        this.inProgress = inProgress;
        this.groupId = groupId;
    }

    public void setStartedAt(LocalDateTime now) {
    }

    public void setEndedAt(LocalDateTime now) {
    }

    public void setInProgress(boolean b) {
    }
}
