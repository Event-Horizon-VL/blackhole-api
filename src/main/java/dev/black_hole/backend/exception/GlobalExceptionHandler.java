package dev.black_hole.backend.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import dev.black_hole.backend.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoResourceFoundException(
            NoResourceFoundException ex,
            HttpServletRequest request) {
        log.warn(
                "Resource not found: method={}, uri={}",
                request.getMethod(),
                request.getRequestURI());
        return build(
                HttpStatus.NOT_FOUND,
                "Resource not found: '%s'".formatted(request.getRequestURI()),
                request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        log.error("Invalid request parameter: name = {}, value = {}",
                ex.getName(),
                ex.getValue());
        return build(
                HttpStatus.BAD_REQUEST,
                "Invalid value for parameter '%s'".formatted(ex.getName()),
                request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(
            Exception ex,
            HttpServletRequest request) {

        log.error(
                "Unexpected exception: {}: {}, RequestURI: {}?{}",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getQueryString());

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
                        Instant.now()));
    }
}