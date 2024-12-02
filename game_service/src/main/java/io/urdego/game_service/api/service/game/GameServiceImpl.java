package io.urdego.game_service.api.service.game;

import io.urdego.game_service.api.controller.game.dto.response.GameRes;
import io.urdego.game_service.common.client.GroupServiceClient;
import io.urdego.game_service.common.client.dto.response.GroupInfoRes;
import io.urdego.game_service.common.exception.ExceptionMessage;
import io.urdego.game_service.common.exception.game.GameException;
import io.urdego.game_service.domain.entity.game.Game;
import io.urdego.game_service.domain.entity.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final GroupServiceClient groupServiceClient;

    // 게임 생성 및 시작
    @Override
    public GameRes startGame(Long groupId) {
        GroupInfoRes groupInfo = groupServiceClient.getGroupInfo(groupId);

        Game game = Game.builder()
                .totalRounds(groupInfo.totalRounds())
                .timer(groupInfo.timer())
                .inProgress(true)
                .playerIds(groupInfo.invitedUsers())
                .groupId(groupInfo.groupId())
                .build();

        game = gameRepository.save(game);
        game.setStartedAt(LocalDateTime.now());

        log.info("Game started at : {}", game.getStartedAt());

        return GameRes.from(game);
    }

    // 게임 종료
    @Override
    public void endGame(Long gameId) {
        Game game = findByGameIdOrThrowGameException(gameId);
        game.setInProgress(false);
        game.setEndedAt(LocalDateTime.now());
        gameRepository.save(game);
    }

    // 게임 정보 조회
    @Override
    @Transactional(readOnly = true)
    public GameRes getGameInfo(Long gameId) {
        Game game = findByGameIdOrThrowGameException(gameId);
        return GameRes.from(game);
    }

    // 공통 메서드
    public Game findByGameIdOrThrowGameException(Long gameId) {
        return gameRepository
                .findById(gameId)
                .orElseThrow(() -> {
                    log.warn("GameId {} : {}", gameId, ExceptionMessage.GAME_NOT_FOUND);
                    return new GameException(ExceptionMessage.GAME_NOT_FOUND.getText());
                });
    }
}
