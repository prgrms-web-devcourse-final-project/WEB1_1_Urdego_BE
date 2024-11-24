package io.urdego.user_service.api.controller;

import static io.urdego.user_service.common.exception.HttpStatusConstants.INTERNAL_SERVER;

import io.urdego.user_service.common.exception.CustomException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> errorResponse(final CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> error(final Exception e, final HttpServletRequest request) {
        return ResponseEntity.status(INTERNAL_SERVER).body(e.getMessage());
    }
}
