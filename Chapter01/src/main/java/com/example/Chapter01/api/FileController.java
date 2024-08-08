package com.example.Chapter01.api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Controller
public class FileController {

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/result")
    public String Result() {
        return "file";
    }

    @GetMapping("/download")
    public ResponseEntity<Object> download() {

        String path = "classpath:static/gameFile/test.zip";

        try {
            Resource resource = resourceLoader.getResource(path);

            if (!resource.exists()) {
                log.error("File not found: " + path);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            InputStreamResource inputStreamResource = new InputStreamResource(resource.getInputStream());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(resource.getFilename()).build());

            return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error occurred", e);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}