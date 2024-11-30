package io.urdego.content_service.domain.entity.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.urdego.content_service.api.user.controller.external.response.UserContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.urdego.content_service.domain.entity.user.QUserContent.userContent;

@Component
@RequiredArgsConstructor
public class UserContentRepositoryImpl implements UserContentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserContentResponse> findUserContentsByUserId_CursorPaging(
            Long userId, Long cursorIdx, Long limit) {

        JPAQuery<UserContentResponse> query =
                queryFactory.select(Projections.constructor(UserContentResponse.class,
                                userContent.userId,
                                userContent.id,
                                userContent.url,
                                userContent.contentName,
                                userContent.address,
                                userContent.latitude,
                                userContent.longitude,
                                userContent.hint,
                                userContent.contentInfo))
                        .from(userContent)
                        .where(userContent.userId.eq(userId))
                        .orderBy(userContent.id.asc());

        if (cursorIdx != null) {
            query = query.where(userContent.id.gt(cursorIdx)); // 먼저 등록한순 (마지막 항목기준 Id 값이 큰값들 조회)
        }

        return query.limit(limit).fetch();
    }


    @Override
    public List<UserContentResponse> findRandomContentsByUserIds(List<Long> userIds) {
        return queryFactory
                .select(Projections.constructor(UserContentResponse.class,
                        userContent.userId,
                        userContent.id,
                        userContent.url,
                        userContent.contentName,
                        userContent.address,
                        userContent.latitude,
                        userContent.longitude,
                        userContent.hint,
                        userContent.contentInfo))
                .from(userContent)
                .where(userContent.userId.in(userIds))
                .fetch();
    }
}
