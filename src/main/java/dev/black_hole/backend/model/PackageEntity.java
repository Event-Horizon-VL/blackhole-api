package dev.black_hole.backend.model;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "package")
public class PackageEntity {

    @Id
    @Column(nullable = false, length = 40)
    private String packageName;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private int revision;

    // TODO String repository

    @Column(nullable = false, name = "size_in_bytes")
    private long sizeInBytes;

    @Column(nullable = false, length = 84)
    private String shortDescription;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "package_archs", joinColumns = @JoinColumn(name = "package_name"))
    Set<Arch> arch;
}
