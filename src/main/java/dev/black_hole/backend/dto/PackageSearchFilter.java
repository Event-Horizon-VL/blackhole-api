package dev.black_hole.backend.dto;

public record PackageSearchFilter(
        String arch,
        String packageName,
        Integer pageSize,
        Integer pageNumber) {
    public PackageSearchFilter {
        // trying to make query params independent of spaces and case
        arch = arch == null ? null : arch.toLowerCase().trim();
        packageName = packageName == null ? null : packageName.toLowerCase().trim();
    }

}