package dev.black_hole.backend.service;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.black_hole.backend.dto.PackageResponseDto;
import dev.black_hole.backend.dto.PackageSearchFilter;
import dev.black_hole.backend.repository.PackageRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PackageService {
    public static final org.slf4j.Logger log = LoggerFactory.getLogger(PackageService.class);

    private final PackageRepository repository;

    @Transactional(readOnly = true)
    public Page<PackageResponseDto> getPackageByFilter(PackageSearchFilter filter) {
        Pageable pageable = getPageable(filter);
        log.debug("Entering getPackageByFilter with params: arch={}, name={}, pageSize={}, pageNumber={}",
                filter.packageName(),
                filter.arch(),
                filter.pageSize(),
                filter.pageNumber());
        return repository.getByPackageNameContainingIgnoreCaseAndArch(
                filter.packageName(),
                filter.arch(),
                pageable);
    }

    private static Pageable getPageable(PackageSearchFilter filter) {
        int pageSize = filter.pageSize() != null
                ? filter.pageSize()
                : 15;
        int pageNumber = filter.pageNumber() != null
                ? filter.pageNumber()
                : 0;

        return Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);
    }
}
