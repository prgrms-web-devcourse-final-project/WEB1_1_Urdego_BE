package io.urdego.user_service.api.service.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NicknameVerificationResult {

    PERMIT(true),
    DUPLICATED(false),
    INAPPROPRIATENESS (false);

    private final Boolean status;
}
