package urdego.common.exception.user;

import urdego.common.exception.ContentException;
import urdego.common.exception.ExceptionMessage;

public class UserContentException extends ContentException {
    public UserContentException(ExceptionMessage message) {
        super(message.getText());
    }
}
