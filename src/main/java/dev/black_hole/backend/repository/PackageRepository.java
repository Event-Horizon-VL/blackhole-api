package dev.black_hole.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.black_hole.backend.model.PackageEntity;

public interface PackageRepository extends JpaRepository<PackageEntity, String> {

    @Query("""
                SELECT p FROM PackageEntity p
                WHERE (:packageName IS NULL OR LOWER(p.packageName) LIKE LOWER(CONCAT('%', :packageName, '%')))
                AND (:arch IS NULL OR :arch MEMBER OF p.arch)
            """)
    Page<PackageEntity> getPackagesByFilter(
            String packageName,
            String arch,
            Pageable pageable);

}
