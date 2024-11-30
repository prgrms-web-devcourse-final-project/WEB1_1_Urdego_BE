package io.urdego.user_service.api.controller.internal.response;

import java.util.List;

public record UserNicknameRequest(
        List<String> nicknames
) {
}
