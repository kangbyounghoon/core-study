package com.example.corestudy.configuration;

import com.example.corestudy.code.SystemOS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Path;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "core.infinispan")
public class InfinispanConfiguration {
    private final Logger logger = LoggerFactory.getLogger(InfinispanConfiguration.class);

    private String cachePath;
    private Path path;

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public Path getCachePath() {
        if (path != null) {
            return path;
        }
        if (SystemOS.getSystemOS() == SystemOS.Window && cachePath.matches("^/.+")) {
            //원도우 일경우 드라이브가 설정이 안되여 있을시 C:할당함.
            return Path.of("C:" + cachePath);
        }
        path = Path.of(cachePath);
        return path;
    }

    @PostConstruct
    private void init() {
        logger.info("cachePath init :: {}", getCachePath());
    }
}
