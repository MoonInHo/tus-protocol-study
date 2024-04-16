package com.example.tusprotocol.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class TusUploadService {

    private final String tusStoragePath;
    private final TusFileUploadService fileUploadService;

    public TusUploadService(
            @Value("${tus.upload.dir}")String tusStoragePath,
            TusFileUploadService fileUploadService
    ) {
        this.tusStoragePath = tusStoragePath;
        this.fileUploadService = fileUploadService;
    }

    public void tusUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, TusException {

        fileUploadService.process(request, response); //TODO 404 WARNING 발생 이유 찾기

        String requestURI = request.getRequestURI();
        UploadInfo uploadInfo = fileUploadService.getUploadInfo(requestURI);

        if (uploadInfo != null) {
            if (!uploadInfo.isUploadInProgress()) {
                File file = new File(tusStoragePath, uploadInfo.getFileName());
                InputStream uploadedBytes = fileUploadService.getUploadedBytes(requestURI);
                FileUtils.copyInputStreamToFile(uploadedBytes, file);
                fileUploadService.deleteUpload(requestURI);
            }
        }
    }
}
