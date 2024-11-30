package io.urdego.game_service.api.service.player;

import io.urdego.game_service.api.controller.player.dto.response.PlayerRes;
import io.urdego.game_service.common.client.GroupServiceClient;
import io.urdego.game_service.common.client.dto.response.GroupInfoRes;
import io.urdego.game_service.common.exception.ExceptionMessage;
import io.urdego.game_service.common.exception.player.PlayerException;
import io.urdego.game_service.domain.entity.player.Player;
import io.urdego.game_service.domain.entity.player.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final GroupServiceClient groupServiceClient;
    private final PlayerRepository playerRepository;

    // 그룹에서 플레이어 정보 매핑
    @Override
    public List<Long> getPlayerIds(Long groupId) {
        GroupInfoRes groupInfo = groupServiceClient.getGroupInfo(groupId);
        if (groupInfo == null || groupInfo.invitedUsers().isEmpty()) {
            throw new PlayerException("GroupId : " + groupId + ExceptionMessage.GROUP_PLAYER_NOT_FOUND.getText());
        }

        return groupInfo.invitedUsers();
    }

    // 플레이어 점수 업데이트
    @Override
    public void updatePlayerTotalScore(Long playerId, int score) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> {
                    log.warn("PlayerId {} : {}", playerId, ExceptionMessage.PLAYER_NOT_FOUND);
                    return new PlayerException(ExceptionMessage.PLAYER_NOT_FOUND.getText());
                });

        int updatedScore = player.getTotalScore() + score;
        player.updateTotalScore(updatedScore);
        playerRepository.save(player);

        log.info("playerId: {}, total score: {}", playerId, updatedScore);
    }

    // 플레이어 총점 조회
    @Transactional(readOnly = true)
    @Override
    public List<PlayerRes> getPlayerTotalScore(Long gameId) {
        List<Player> players = playerRepository.findAllByGameId(gameId);

        return players.stream().map(PlayerRes::from).toList();
    }
}
