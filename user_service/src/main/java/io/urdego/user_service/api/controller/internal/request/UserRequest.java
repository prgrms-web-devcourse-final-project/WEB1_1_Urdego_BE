package io.urdego.user_service.api.controller.internal.request;

public record UserRequest(
        String nickname
) {
    public static UserRequest of(String nickname) {
        return new UserRequest(nickname);
    }
}
