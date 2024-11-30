package io.urdego.group_service.common.client.request;

import java.util.List;

public record UserNicknameRequest(
        List<String> nicknames
) {
    public static UserNicknameRequest of(List<String> nicknames) {
        return new UserNicknameRequest(nicknames);
    }
}
