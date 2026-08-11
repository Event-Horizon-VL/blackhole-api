package dev.black_hole.backend.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import dev.black_hole.backend.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.BAD_REQUEST,
                "INVALID_ARGUMENT",
                "Invalid value for parameter: " + ex.getName(),
                request);
    }

    private ResponseEntity<ErrorResponseDto> build(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request) {

        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDto(
                        code,
                        message,
                        request.getRequestURI(),
                        LocalDateTime.now()));
    }
}
