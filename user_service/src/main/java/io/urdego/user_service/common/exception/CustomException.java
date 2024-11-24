package io.urdego.user_service.common.exception;

public abstract class CustomException extends RuntimeException {

     public abstract Integer getStatus();
     public abstract String getMessage();
}
