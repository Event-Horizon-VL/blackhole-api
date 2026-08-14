package dev.black_hole.backend.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.black_hole.backend.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected server error", ex);

        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                request);
    }

    private ResponseEntity<ErrorResponseDto> build(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDto(
                        status.getReasonPhrase(),
                        message,
                        request.getRequestURI(),
                        LocalDateTime.now()));
    }
}