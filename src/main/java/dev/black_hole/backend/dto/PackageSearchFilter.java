package dev.black_hole.backend.dto;

public record PackageSearchFilter(
        String arch,
        String packageName,
        Integer pageSize,
        Integer pageNumber) {
}