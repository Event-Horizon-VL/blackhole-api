package dev.black_hole.backend.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "blackhole.repodata")
public class RepodataProperties {

    private String path;
    private Map<String, String> files;

}