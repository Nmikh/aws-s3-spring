package com.controllers;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/file")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        PutObjectResult putObjectResult = s3Service.uploadFile(file.getOriginalFilename(), file);
        return new ResponseEntity(putObjectResult, HttpStatus.OK);

    }

    @GetMapping("/file/{file_id}")
    public ResponseEntity downloadFile(@PathVariable("file_id") String fileId) {
        S3ObjectInputStream s3ObjectInputStream = s3Service.downloadFile(fileId);
        InputStreamResource inputStreamResource = new InputStreamResource(s3ObjectInputStream);
        return new ResponseEntity(inputStreamResource, HttpStatus.OK);
    }
}
