package com.project.controller;

import java.awt.PageAttributes.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.gridfs.model.GridFSFile;

@RestController
@RequestMapping("/api/download")
public class FileDownloadController {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @GetMapping("/{filename}")
    public ResponseEntity<String> downloadFile(@PathVariable String filename) {
        try {
            GridFSFile gridFSFile = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is(filename)));
            if (gridFSFile != null) {
                GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                        .body(new InputStreamResource(resource.getInputStream()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (AccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File download failed: " + e.getMessage());
        }
    }
}
