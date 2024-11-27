package io.urdego.user_service.common.exception;

import static io.urdego.user_service.common.constant.HttpStatusConstants.NOT_FOUND;

public class UserNotFoundException extends CustomException {
    private static final Integer STATUS = NOT_FOUND;
    private static final String ERROR_MESSAGE = "해당 회원이 존재하지 않습니다.";

    @Override
    public Integer getStatus() {
        return STATUS;
    }

    @Override
    public String getMessage() {
        return ERROR_MESSAGE;
    }
}
