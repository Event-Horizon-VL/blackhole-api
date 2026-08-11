package dev.black_hole.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import dev.black_hole.backend.dto.PackageResponseDto;
import dev.black_hole.backend.model.PackageEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PackageMapper {
    PackageResponseDto toPackageResponseDto(PackageEntity packageEntity);
}
