package io.urdego.user_service.api.service;

import io.urdego.user_service.api.controller.external.request.SignUpRequest;
import io.urdego.user_service.api.controller.external.response.UserInfoResponse;
import io.urdego.user_service.api.controller.internal.response.UserInfo;

import java.util.List;

public interface UserService {

    // 회원가입
    // 생성된 userId 반환
    Long signUp(SignUpRequest signUpRequest);

    // 닉네임 검증
    NicknameVerificationResult verifyNickname(String nickname);

    // 로그인
    // 유저 닉네임 반환
    String login(String email, String password);

    //닉네임으로 유저 검색
    List<UserInfoResponse> findByNickname(String string);

    //    UserDetail getDetail(Long userId);

    // 친구추가, 그룹초대 등에서 사용
    // 찾은 userId 반환
    //    Optional<Long> findUser(String nickname);

    // 수정한 userId 반환
    //    Long editInfo(Long userId, UserEditInfo userEditInfo);

    //    void withdraw(Long userId);

    // email로 user 조회
    // Content 등록 시 UserId 통신
    // group 등록 시 UserId 통신
    UserInfo findUserByEmail(String email);
}
