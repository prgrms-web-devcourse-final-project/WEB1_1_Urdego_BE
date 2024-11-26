package io.urdego.content_service.common.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponse {

    private Long userId;

    private String email;

    private String nickname;

    @Builder
    public UserInfoResponse(Long userId, String email, String nickname) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }
}
