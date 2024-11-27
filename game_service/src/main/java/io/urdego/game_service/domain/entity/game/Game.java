package io.urdego.game_service.domain.entity.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    private LocalDateTime started_at;
    private LocalDateTime ended_at;

    // FK
    @Column(nullable = false)
    private Long groupId;
}
