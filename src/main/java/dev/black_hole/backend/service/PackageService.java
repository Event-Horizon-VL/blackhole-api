package dev.black_hole.backend.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;

import dev.black_hole.backend.config.RepodataProperties;
import dev.black_hole.backend.dto.PackageResponseDto;
import dev.black_hole.backend.dto.PackageSearchFilter;
import dev.black_hole.backend.mapper.PackageMapper;
import dev.black_hole.backend.model.Arch;
import dev.black_hole.backend.model.PackageEntity;
import dev.black_hole.backend.parser.Parser;
import dev.black_hole.backend.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PackageService {

    private final PackageRepository repository;
    private final RepodataProperties properties;
    private final PackageMapper packageMapper;

    public Page<PackageResponseDto> searchPackages(PackageSearchFilter filter) {
        Pageable pageable = filter.pageable();
        log.debug(
                "Entering getPackageByFilter with params: arch={}, name={}, pageSize={}, pageNumber={}",
                filter.arch(),
                filter.packageName(),
                pageable.getPageSize(),
                pageable.getPageNumber());

        Arch arch = Arch.fromCode(filter.arch());
        if (filter.arch() != null && arch == null) {
            log.warn("Unknown architecture received: {}", filter.arch());
        }

        return repository.getPackagesByFilter(
                filter.packageName(),
                arch,
                pageable).map(packageMapper::toPackageResponseDto);
    }

    @Transactional
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void addAllPackagesFromRepodataToDb() {

        String repodataFilePathOfX86_64 = getRepodataFilePath(Arch.X86_64);

        NSDictionary repodata_x86_64 = null;
        try {
            repodata_x86_64 = Parser.toMap(new File(repodataFilePathOfX86_64))
                    .orElseThrow(() -> new FileNotFoundException());

            List<PackageEntity> packages = parsePackages(repodata_x86_64);

            for (Arch arch : Arch.values()) {
                if (arch == Arch.X86_64) {
                    continue;

                }

                String repodataFilePath = getRepodataFilePath(arch);

                NSDictionary repodata = Parser.toMap(new File(repodataFilePath))
                        .orElseThrow(() -> new FileNotFoundException());

                for (PackageEntity packageEntity : packages) {
                    if (repodata.containsKey(packageEntity.getPackageName())) {
                        packageEntity.getArch().add(arch);
                    }
                }

                repository.saveAll(packages);
            }
        } catch (FileNotFoundException e) {
            log.error(
                    "File not found: {}: {} in addAllPackagesFromRepodataToDb",
                    e.getClass().getSimpleName(),
                    e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(
                    "Invalid package data: {}: {} in addAllPackagesFromRepodataToDb",
                    e.getClass().getSimpleName(),
                    e.getMessage());
        } catch (IOException | PropertyListFormatException | ParseException
                | ParserConfigurationException | SAXException e) {
            log.error(
                    "Failed to parse repodata: {}: {} in addAllPackagesFromRepodataToDb",
                    e.getClass().getSimpleName(),
                    e.getMessage());
        } catch (Exception e) {
            log.error(
                    "Unexpected exception: {}: {} in addAllPackagesFromRepodataToDb",
                    e.getClass().getSimpleName(),
                    e.getMessage());
        }

    }

    private List<PackageEntity> parsePackages(NSDictionary repodata) {

        List<PackageEntity> list = repodata.keySet().stream()
                .map(packageName -> {
                    NSDictionary metadata = (NSDictionary) repodata.get(packageName);

                    PackageEntity packageEntity = new PackageEntity();

                    packageEntity.setPackageName(packageName);

                    String pkgver = metadata.get("pkgver").toString();

                    int dash = pkgver.lastIndexOf('-');
                    int underscore = pkgver.lastIndexOf('_');

                    packageEntity.setVersion(pkgver.substring(dash + 1, underscore));
                    packageEntity.setRevision(Integer.parseInt(pkgver.substring(underscore + 1)));

                    packageEntity.setSizeInBytes(
                            Long.parseLong(metadata.get("filename-size").toString()));

                    packageEntity.setShortDescription(
                            metadata.get("short_desc").toString());

                    Set<Arch> archs = new HashSet<>();
                    archs.add(
                            Arch.fromCode(metadata.get("architecture").toString()));

                    packageEntity.setArch(archs);
                    return packageEntity;
                })
                .toList();
        return list;
    }


    private String getRepodataFilePath(Arch arch) {
        return properties.getPath() + "/" +
                properties.getFiles().get(arch.getValue());
    }
}
