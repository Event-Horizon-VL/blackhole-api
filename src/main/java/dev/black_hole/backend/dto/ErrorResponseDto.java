package dev.black_hole.backend.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto(
                String error,
                String message,
                String path,
                LocalDateTime errorTime) {

}
