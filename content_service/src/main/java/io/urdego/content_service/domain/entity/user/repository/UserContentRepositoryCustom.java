package io.urdego.content_service.domain.entity.user.repository;

import io.urdego.content_service.api.user.controller.external.response.UserContentResponse;

import java.util.List;

public interface UserContentRepositoryCustom {

    // userId를 통해 해당 유저의 컨텐츠를 조회한다.
    List<UserContentResponse> findUserContentsByUserId_CursorPaging(Long userId, Long cursorIdx, Long limit);

    // userIds를 통해 게임 컨텐츠를 랜덤 조회한다.
    List<UserContentResponse> findRandomContentsByUserIds(List<Long> userIds);

    // userId를 통해 유저의 전체 컨텐츠를 반환한다.
    Long countUserContentsByUserId(Long userId);
}
