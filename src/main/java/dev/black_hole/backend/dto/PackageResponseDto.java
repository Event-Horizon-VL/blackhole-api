package dev.black_hole.backend.dto;

import java.util.Set;

import dev.black_hole.backend.model.Arch;

public record PackageResponseDto(
        String packageName,
        String version,
        int revision,
        long sizeInBytes,
        String shortDescription,
        Set<Arch> arch) {

}
