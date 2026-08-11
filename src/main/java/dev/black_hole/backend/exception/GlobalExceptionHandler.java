package dev.black_hole.backend.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dd.plist.PropertyListFormatException;

import dev.black_hole.backend.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleFileNotFound(
            FileNotFoundException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.BAD_REQUEST,
                "FILE_NOT_FOUND",
                "Package file not found",
                request);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDto> handleIOException(
            IOException ex,
            HttpServletRequest request) {

        log.error("IO error while parsing package", ex);

        return build(
                HttpStatus.BAD_REQUEST,
                "FILE_READ_ERROR",
                "Cannot read package file",
                request);
    }

    @ExceptionHandler(PropertyListFormatException.class)
    public ResponseEntity<ErrorResponseDto> handlePlistError(
            PropertyListFormatException ex,
            HttpServletRequest request) {

        log.error("Invalid plist format", ex);

        return build(
                HttpStatus.BAD_REQUEST,
                "INVALID_PLIST",
                "Package metadata is corrupted",
                request);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponseDto> handleParseError(
            ParseException ex,
            HttpServletRequest request) {

        log.error("Parse error", ex);

        return build(
                HttpStatus.BAD_REQUEST,
                "PARSE_ERROR",
                "Cannot parse package metadata",
                request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected server error", ex);

        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
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