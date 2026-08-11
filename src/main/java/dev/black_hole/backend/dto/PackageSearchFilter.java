package dev.black_hole.backend.dto;

import dev.black_hole.backend.model.Arch;

public record PackageSearchFilter(
        Arch arch,
        String packageName,
        Integer pageSize,
        Integer pageNumber) {
}