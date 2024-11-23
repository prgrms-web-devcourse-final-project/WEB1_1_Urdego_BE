package io.urdego.user_service.api.controller.request;

public record SignUpRequest(

        String email,

        String password,

        String nickname

//    소셜로그인 제공자
//    private String provider;

//    랭킹 점수
//    private Integer rating;

) {
}
