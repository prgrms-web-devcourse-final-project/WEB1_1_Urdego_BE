package io.urdego.group_service.common.exception;

import feign.FeignException;
import io.urdego.group_service.common.exception.group.GroupException;
import io.urdego.group_service.common.exception.groupMember.GroupMemberException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // GroupException 처리
    @ExceptionHandler(GroupException.class)
    public ResponseEntity<ErrorResponse> handleGroupNotFoundException(GroupException e) {
        ErrorResponse errorResponse =
                ErrorResponse.from(HttpStatus.NOT_FOUND.value(), "Group Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // GroupMemberException 처리
    @ExceptionHandler(GroupMemberException.class)
    public ResponseEntity<ErrorResponse> handleGroupMemberNotFoundException(
            GroupMemberException e) {
        ErrorResponse errorResponse =
                ErrorResponse.from(
                        HttpStatus.NOT_FOUND.value(), "Group Member Not Found", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // BaseException 처리
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        ErrorResponse errorResponse =
                ErrorResponse.from(HttpStatus.BAD_REQUEST.value(), "Bad Request", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse =
                ErrorResponse.from(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {

        String errorMsg = e.getMessage();

        ErrorResponse error =
                ErrorResponse.from(e.status(), BAD_REQUEST.getReasonPhrase(), errorMsg);

        return ResponseEntity.badRequest().body(error);
    }
}
