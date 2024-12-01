package io.urdego.group_service.common.client.response;

import java.util.List;

public record UserIdListResponse(
        List<Long> userIds
) {
}
