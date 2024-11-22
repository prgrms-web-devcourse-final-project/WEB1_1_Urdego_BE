package io.urdego.user_service.user.application.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {

    private Long id;

    private String email;

    private String password;

    private String nickname;

//    소셜로그인 제공자
//    private String provider;

//    랭킹 점수
//    private Integer rating;

    private Role role;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
