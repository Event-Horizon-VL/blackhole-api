package dev.black_hole.backend.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.black_hole.backend.dto.PackageResponseDto;
import dev.black_hole.backend.model.PackageEntity;

public interface PackageRepository extends JpaRepository<PackageEntity, UUID> {

    Page<PackageResponseDto> getByPackageNameContainingIgnoreCaseAndArch(
            String packageName,
            String arch,
            Pageable pageable);

}
