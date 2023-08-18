package com.upload.domain.core.upload;

import com.upload.domain.core.FileUtil;
import com.upload.domain.core.load.LoadService;
import com.upload.exception.BusinessException;
import com.upload.infraestructure.entity.Load;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Autowired
    LoadService loadService;

    @Autowired
    ProcessFileService processFileService;

    @Value("${config.multipart.extensions}")
    private String extensions;


    @Override
    public Load loadFile(MultipartFile multipartFile) throws BusinessException {
        FileUtil.validateFile(multipartFile, extensions);
        Load load = loadService.createLoad(multipartFile.getOriginalFilename(), "EN PROCESO");
        processFileService.processFile(multipartFile, load);
        return load;
    }
}
