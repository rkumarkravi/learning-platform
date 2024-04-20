package com.rk.olms.dtos.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadReqDto {
    private String filePath;
    private MultipartFile file;
    private Long courseId;
}
