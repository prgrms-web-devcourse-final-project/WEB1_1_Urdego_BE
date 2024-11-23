package io.urdego.user_service.api.service;

import io.urdego.user_service.api.controller.request.SignUpRequest;
import io.urdego.user_service.api.service.constant.NicknameVerificationResult;

public interface UserService {

    //회원가입
    //생성된 userId 반환
    Long signUp(SignUpRequest signUpRequest);

    //닉네임 검증
    NicknameVerificationResult verifyNickname(String nickname);

    //로그인
    //유저 닉네임 반환
    String login(String email, String password);

//    UserDetail getDetail(Long userId);

    //친구추가, 그룹초대 등에서 사용
    //찾은 userId 반환
//    Optional<Long> findUser(String nickname);

    //수정한 userId 반환
//    Long editInfo(Long userId, UserEditInfo userEditInfo);

//    void withdraw(Long userId);
}
