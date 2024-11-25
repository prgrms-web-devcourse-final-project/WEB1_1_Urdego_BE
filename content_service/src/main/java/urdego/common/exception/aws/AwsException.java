package urdego.common.exception.aws;

import urdego.common.exception.ContentException;
import urdego.common.exception.ExceptionMessage;

public class AwsException extends ContentException {
    public AwsException(ExceptionMessage message) {
        super(message.getText());
    }
}
