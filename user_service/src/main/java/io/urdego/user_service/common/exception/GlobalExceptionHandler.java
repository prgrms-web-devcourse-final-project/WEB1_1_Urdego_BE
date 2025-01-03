package io.urdego.user_service.common.exception;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static io.urdego.user_service.common.constant.HttpStatusConstants.INTERNAL_SERVER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> errorResponse(final CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> error(final Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER).body(e.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {

        String errorMsg = e.getMessage();

        ErrorResponse error =
                ErrorResponse.from(e.status(), BAD_REQUEST.getReasonPhrase(), errorMsg);

        return ResponseEntity.badRequest().body(error);
    }
}
