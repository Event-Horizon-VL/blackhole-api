package dev.black_hole.backend.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID packageId;

    @Column(nullable = false,length = 40)
    private String packageName;

    @Column(nullable = false)
    private Double version;

    @Column(nullable = false)
    private int revision;

    // TODO String repository

    @Column(nullable = false)
    private long sizeInBytes;

    @Column(nullable = false, length = 84)
    private String shortDescription;

}
