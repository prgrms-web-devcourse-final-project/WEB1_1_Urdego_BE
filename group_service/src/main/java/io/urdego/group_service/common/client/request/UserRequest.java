package io.urdego.group_service.common.client.request;

public record UserRequest(
        String nickname
) {

    public static UserRequest of(String nickname) {
        return new UserRequest(nickname);
    }
}
