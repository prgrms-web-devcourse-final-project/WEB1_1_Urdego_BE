package io.urdego.user_service.api.controller.internal.response;

import java.util.List;

public record UserIdListResponse(
        List<Long> userIds
) {
    public static UserIdListResponse of(final List<Long> ids) {
        return new UserIdListResponse(ids);
    }
}
