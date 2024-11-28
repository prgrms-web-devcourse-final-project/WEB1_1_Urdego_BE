package io.urdego.content_service.domain.entity.user.repository;

import io.urdego.content_service.api.user.controller.response.UserContentResponse;

import java.util.List;

public interface UserContentRepositoryCustom {

    // userId를 통해 해당 유저의 컨텐츠를 조회한다.
    List<UserContentResponse> findUserContentsByUserId_CursorPaging(Long userId, Long cursorIdx, Long limit);
}
