package dev.black_hole.backend.dto;

public record PackageResponseDto(
        String packageName,
        Double version,
        int revision,
        long sizeInBytes,
        String shortDescription) {

}
