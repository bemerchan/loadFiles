package com.upload.application.api;

import com.upload.domain.core.load.LoadService;
import com.upload.domain.core.upload.UploadService;
import com.upload.exception.BusinessException;
import com.upload.infraestructure.entity.Load;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadApi {

    @Autowired
    LoadService loadService;

    @Autowired
    UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<Load> upload(@RequestParam(value = "file") MultipartFile file) throws BusinessException {
        return new ResponseEntity<>(uploadService.loadFile(file), HttpStatus.OK);
    }
}
