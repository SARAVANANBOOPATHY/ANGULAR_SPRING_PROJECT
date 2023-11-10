package com.project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.service.FileStorageService;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String compressedFileName = fileStorageService.storeAndCompressFile(file);
            return ResponseEntity.ok("File uploaded and compressed as: " + compressedFileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }
}