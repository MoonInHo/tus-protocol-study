package com.example.tusprotocol.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.desair.tus.server.exception.TusException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(exposedHeaders = {"Upload-Offset", "Location"})
@RequiredArgsConstructor
public class UploadController {

    private final TusUploadService tusUploadService;

    @RequestMapping(
            value = {"/upload", "/upload/**"},
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD, RequestMethod.PATCH, RequestMethod.DELETE}
    )
    public ResponseEntity<Void> tusUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, TusException {
        tusUploadService.tusUpload(request, response);
        return ResponseEntity.ok().build();
    }
}
