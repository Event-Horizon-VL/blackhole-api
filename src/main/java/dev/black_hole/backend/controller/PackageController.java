package dev.black_hole.backend.controller;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.black_hole.backend.dto.PackageResponseDto;
import dev.black_hole.backend.dto.PackageSearchFilter;
import dev.black_hole.backend.service.PackageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
public class PackageController {
    public static final org.slf4j.Logger log = LoggerFactory.getLogger(PackageController.class);
    private final PackageService packageService;
    @GetMapping("/")
    public ResponseEntity<Page<PackageResponseDto>> getPackageByFilter(
            @RequestParam(required = false) String arch,
            @RequestParam(name = "name", required = false) String packageName,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber) {
        var filter = new PackageSearchFilter(arch, packageName, pageSize, pageNumber);
        log.info("Getting package with filter={}", filter);
        return ResponseEntity.status(HttpStatus.OK)
                .body(packageService.getPackageByFilter(filter));
    }
}