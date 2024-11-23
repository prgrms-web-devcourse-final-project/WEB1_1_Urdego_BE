package io.urdego.user_service.api.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NicknameVerificationResult {

    PERMIT("PERMIT"),
    DUPLICATED("DUPLICATED"),
    INAPPROPRIATENESS ("INAPPROPRIATENESS");

    private final String status;

}
