package com.example.tusprotocol.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class FileCleanupScheduler {

    private final TusFileUploadService tusFileUploadService;

    @Scheduled(fixedDelayString = "PT12H")
    public void cleanup() throws IOException {
        log.info("clean up");
        tusFileUploadService.cleanup();
    }
}
