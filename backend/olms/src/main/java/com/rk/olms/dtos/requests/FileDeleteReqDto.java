package com.rk.olms.dtos.requests;

import lombok.Data;

@Data
public class FileDeleteReqDto {
    private String filePath;
    private Long courseId;
}
