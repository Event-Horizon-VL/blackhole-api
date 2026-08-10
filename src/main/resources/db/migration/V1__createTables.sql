CREATE TABLE package (
    package_id UUID PRIMARY KEY,
    package_name VARCHAR(40) NOT NULL,
    version TEXT NOT NULL,
    revision INTEGER NOT NULL,
    size_in_bytes BIGINT NOT NULL,
    short_description VARCHAR(84) NOT NULL
);

CREATE TABLE package_archs (
    package_id UUID NOT NULL,
    arch VARCHAR(30) NOT NULL,

    CONSTRAINT fk_package_archs_package
        FOREIGN KEY (package_id)
        REFERENCES package (package_id)
        ON DELETE CASCADE
);