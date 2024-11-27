package io.urdego.content_service.common.exception.user;

import io.urdego.content_service.common.exception.ContentException;
import io.urdego.content_service.common.exception.ExceptionMessage;

public class UserContentException extends ContentException {
    public UserContentException(ExceptionMessage message) {
        super(message.getText());
    }
}
