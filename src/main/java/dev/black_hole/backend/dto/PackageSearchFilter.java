package dev.black_hole.backend.dto;

import org.springframework.data.domain.Pageable;

public record PackageSearchFilter(
        String arch,
        String packageName,
        Pageable pageable) {
    public PackageSearchFilter {
        // trying to make query params independent of spaces and case
        arch = arch == null ? null : arch.toLowerCase().trim();
        packageName = packageName == null ? null : packageName.toLowerCase().trim();
    }

}