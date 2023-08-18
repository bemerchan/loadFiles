package com.upload.domain.core;

import com.upload.exception.BusinessException;
import org.apache.commons.io.FilenameUtils;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    public static void validateFile(MultipartFile multipartFile, String extensions) throws BusinessException {
        if (multipartFile.isEmpty()) {
            throw new BusinessException(MessageUtil.FILE_IS_EMTY);
        }
        List<String> listExtensions = Arrays.asList(extensions.split(","));
        if(!listExtensions.contains(getExtensionFile(multipartFile))) {
            throw new BusinessException(MessageUtil.INCORRECT_FILE_FORMAT);
        }
    }

    public static BeanReader readData(MultipartFile multipartFile) throws BusinessException {
        File file = convertMultiPartToFile(multipartFile);
        StreamFactory factory = StreamFactory.newInstance();
        factory.load("src/main/resources/load-mapping.xml");
        return factory.createReader("formatLoadFile", file);
    }

    private static File convertMultiPartToFile(MultipartFile file ) throws BusinessException {
        File convFile = new File( file.getOriginalFilename() );
        try {
            FileOutputStream fos = new FileOutputStream( convFile);
            fos.write( file.getBytes() );
            fos.close();
        } catch (IOException e) {
            throw new BusinessException(MessageUtil.ERROR_CONVERTING_FILE + e.getMessage());
        }
        return convFile;
    }

    private static String getExtensionFile(MultipartFile multipartFile) {
        return FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();
    }

    private FileUtil() {
        throw new IllegalStateException("File class");
    }
}
