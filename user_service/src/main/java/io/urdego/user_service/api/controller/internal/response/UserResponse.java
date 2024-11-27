package io.urdego.user_service.api.controller.internal.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

    private Long userId;

    private String email;

    private String nickname;

    @Builder
    public UserResponse(Long userId, String email, String nickname) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }
}
