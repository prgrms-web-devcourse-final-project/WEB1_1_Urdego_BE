package io.urdego.user_service.domain.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    //    ADMIN("관리자"),

    UNAUTH("미인증"),

    USER("유저"),

    WITHDRAW("탈퇴");

    private final String text;
}
