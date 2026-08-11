CREATE TABLE package (
    package_name VARCHAR(40) NOT NULL PRIMARY KEY,
    version TEXT NOT NULL,
    revision INTEGER NOT NULL,
    size_in_bytes BIGINT NOT NULL,
    short_description VARCHAR(84) NOT NULL
);
CREATE TABLE package_archs (
    package_name VARCHAR(40) NOT NULL,
    arch VARCHAR(30) NOT NULL,
    CONSTRAINT fk_package_archs_package FOREIGN KEY (package_name) REFERENCES package (package_name) ON DELETE CASCADE
);