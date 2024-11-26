package io.urdego.content_service.common.exception.aws;

import io.urdego.content_service.common.exception.ContentException;
import io.urdego.content_service.common.exception.ExceptionMessage;

public class AwsException extends ContentException {
    public AwsException(ExceptionMessage message) {
        super(message.getText());
    }
}
