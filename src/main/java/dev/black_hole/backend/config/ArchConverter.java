package dev.black_hole.backend.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dev.black_hole.backend.model.Arch;

@Component
public class ArchConverter implements Converter<String, Arch> {

    @Override
    public Arch convert(String source) {
        return Arch.fromCode(source);
    }
}
