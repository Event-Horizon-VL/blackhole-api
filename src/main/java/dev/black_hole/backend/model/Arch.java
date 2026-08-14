package dev.black_hole.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Arch {
    X86_64("x86_64"),
    X86_64_MUSL("x86_64-musl"),
    AARCH64("aarch64"),
    AARCH64_MUSL("aarch64-musl");

    private final String value;

    public static Arch fromCode(String value) {
        for (Arch arch : values()) {
            if (arch.value.equals(value)) {
                return arch;
            }
        }
        // if value is not in values()
        return null;
    }

}
