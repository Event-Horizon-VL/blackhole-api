package dev.black_hole.backend.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Arch {
    X86_64("x86_64"),
    X86_64_MUSL("x86_64-musl"),
    AARCH64("aarch64"),
    AARCH64_MUSL("aarch64-musl");

    private final String value;

    public static Arch fromCode(String value) {
        for (Arch arch : values()) {
            if (arch.value == value) {
                return arch;
            }
        }
        throw new IllegalArgumentException("Unknown Arch: " + value);
    }

}
