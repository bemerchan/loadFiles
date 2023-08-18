package com.upload.domain.core.upload;

import com.upload.exception.BusinessException;
import com.upload.infraestructure.entity.Load;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    Load loadFile(MultipartFile file) throws BusinessException;
}
