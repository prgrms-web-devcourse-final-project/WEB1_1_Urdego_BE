package io.urdego.user_service.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static io.urdego.user_service.common.constant.HttpStatusConstants.INTERNAL_SERVER;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> errorResponse(final CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> error(final Exception e) {
        return ResponseEntity
                .status(INTERNAL_SERVER)
                .body(e.getMessage());
    }
}
