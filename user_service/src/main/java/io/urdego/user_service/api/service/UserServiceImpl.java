package io.urdego.user_service.api.service;

import io.urdego.user_service.api.controller.request.SignUpRequest;
import io.urdego.user_service.api.service.exception.UserNotFoundException;
import io.urdego.user_service.domain.define.User;
import io.urdego.user_service.domain.define.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long signUp(final SignUpRequest signUpRequest) {
        User save = userRepository.save(
                User.builder().
                        email(signUpRequest.email()).
                        password(signUpRequest.password()).
                        nickname(signUpRequest.nickname()).
                        build());
        return save.getId();
    }

    @Override
    public NicknameVerificationResult verifyNickname(final String nickname) {
        if (userRepository.existsByNickname(nickname))
            return NicknameVerificationResult.DUPLICATED;
        return NicknameVerificationResult.PERMIT;
    }

    @Override
    public String login(final String email, final String password) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return user.getNickname();
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
