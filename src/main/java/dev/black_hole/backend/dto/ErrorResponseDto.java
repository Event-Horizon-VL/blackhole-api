package dev.black_hole.backend.dto;

import java.time.Instant;

public record ErrorResponseDto(
        String error,
        String message,
        String path,
        Instant errorTime) {

}
