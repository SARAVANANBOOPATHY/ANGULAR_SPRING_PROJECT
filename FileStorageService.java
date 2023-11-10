package com.project.service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public String storeAndCompressFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String compressedFileName = "compressed_" + originalFileName;

        // Compress the file
        byte[] compressedData = compressFile(file.getBytes());

        // Store the compressed file in MongoDB
        ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
        gridFsTemplate.store(inputStream, compressedFileName);

        return compressedFileName;
    }

    public InputStream retrieveCompressedFile(String compressedFileName) throws IOException {
    	gridFsTemplate.findOne(new Query(Criteria.where("filename").is(compressedFileName)));
        return null;
    }

    private byte[] compressFile(byte[] data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data);
        }
        return byteArrayOutputStream.toByteArray();
    }
}