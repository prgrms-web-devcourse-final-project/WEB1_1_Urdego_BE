package io.urdego.game_service.api.service.round;

import io.urdego.game_service.api.controller.round.dto.request.RoundCreateReq;
import io.urdego.game_service.api.controller.round.dto.response.RoundRes;
import io.urdego.game_service.common.client.ContentServiceClient;
import io.urdego.game_service.common.client.GroupServiceClient;
import io.urdego.game_service.common.client.dto.response.ContentRes;
import io.urdego.game_service.common.exception.ExceptionMessage;
import io.urdego.game_service.common.exception.content.ContentException;
import io.urdego.game_service.common.exception.game.GameException;
import io.urdego.game_service.domain.entity.game.Game;
import io.urdego.game_service.domain.entity.game.repository.GameRepository;
import io.urdego.game_service.domain.entity.round.Round;
import io.urdego.game_service.domain.entity.round.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoundServiceImpl implements RoundService{

    private final RoundRepository roundRepository;
    private final GameRepository gameRepository;
    private final ContentServiceClient contentServiceClient;
    private final GroupServiceClient groupServiceClient;

    // 라운드 생성
    @Override
    public RoundRes createRound(RoundCreateReq request) {

        List<ContentRes> contentList = contentServiceClient.getUserContents(request.playerIds());
        List<Long> contentIds = contentList.stream()
                .map(ContentRes::contentId)
                .toList();

        Round round = Round.builder()
                .gameId(request.gameId())
                .roundNum(request.roundNum())
                .contentIds(contentIds)
                .build();
        round = roundRepository.save(round);

        if (contentList.isEmpty()) {
            log.warn("{} / PlayerIds : {}", ExceptionMessage.ROUND_CONTENT_NOT_FOUND.getText(), request.playerIds());
            throw new ContentException(ExceptionMessage.ROUND_CONTENT_NOT_FOUND.getText());
        }

        log.info("Round {} created with {} problems", round.getRoundNum(), contentList.size());

        return RoundRes.from(round);
    }

//    // 그룹-게임 정보 매핑 내부 메서드
//    public Long findGroupId(Long gameId) {
//        return gameRepository.findById(gameId)
//                .map(Game::getGroupId)
//                .orElseThrow(() -> {
//                    log.warn("GameId {} : {}", gameId, ExceptionMessage.GAME_NOT_FOUND);
//                    return new GameException(ExceptionMessage.GAME_NOT_FOUND.getText());
//                });
//    }
}
