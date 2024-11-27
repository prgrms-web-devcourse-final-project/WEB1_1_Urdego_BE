package io.urdego.user_service.api.service;

import io.urdego.user_service.api.controller.external.request.SignUpRequest;
import io.urdego.user_service.api.controller.external.response.UserInfoResponse;
import io.urdego.user_service.api.controller.internal.response.UserInfo;
import io.urdego.user_service.api.controller.internal.response.UserResponse;
import io.urdego.user_service.common.exception.UserNotFoundException;
import io.urdego.user_service.domain.UserRepository;
import io.urdego.user_service.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long signUp(final SignUpRequest signUpRequest) {
        User save =
                userRepository.save(
                        User.builder()
                                .email(signUpRequest.email())
                                .password(signUpRequest.password())
                                .nickname(signUpRequest.nickname())
                                .build());
        return save.getId();
    }

    @Override
    public NicknameVerificationResult verifyNickname(final String nickname) {
        if (userRepository.existsByNickname(nickname)) return NicknameVerificationResult.DUPLICATED;
        return NicknameVerificationResult.PERMIT;
    }

    @Override
    public String login(final String email, final String password) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return user.getNickname();
    }

    @Override
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    @Override
    public List<UserInfoResponse> findByNickname(final String string) {
        return userRepository.findByString(string).stream().map(UserInfoResponse::from).toList();
    }

    @Override
    public UserInfo findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return UserInfo.convertToUserInfo(user);
    }

    //    @Override
    //    public UserDetail getDetail(final Long userId) {
    //        User user = userReader.read(userId);
    //        return UserDetail.from(user);
    //    }
    //
    //    @Override
    //    public Optional<Long> findId(final String nickname) {
    //        return userReader.readOptional(nickname);
    //    }
    //
    //    @Override
    //    @Transactional
    //    public Long editInfo(final Long userId, final UserEditInfo userEditInfo) {
    //        return null;
    //    }
    //
    //    @Override
    //    @Transactional
    //    public void withdraw(final Long userId) {
    //
    //    }
}
