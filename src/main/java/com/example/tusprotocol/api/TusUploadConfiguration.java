package com.example.tusprotocol.api;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Slf4j
@Configuration
public class TusUploadConfiguration {

    private final String tusStoragePath;
    private final Long tusUploadExpirationPeriod;

    private final ServletContext servletContext;

    public TusUploadConfiguration(
            @Value("${tus.upload.dir}")String tusStoragePath,
            @Value("${tus.upload.expiration}")Long tusUploadExpirationPeriod,
            ServletContext servletContext
    ) {
        this.tusStoragePath = tusStoragePath;
        this.tusUploadExpirationPeriod = tusUploadExpirationPeriod;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        if (new File("$tusStoragePath/uploads").mkdirs()) {
            log.info("Created tus upload directory");
        }
        if (new File("$tusStoragePath/locks").mkdirs()) {
            log.info("Created tus lock directory");
        }
    }

    @Bean
    public TusFileUploadService tusFileUploadService() {
        return new TusFileUploadService()
                .withStoragePath(tusStoragePath)
                .withDownloadFeature()
                .withUploadExpirationPeriod(tusUploadExpirationPeriod)
                .withUploadUri(servletContext.getContextPath() + "/upload");
    }
}
