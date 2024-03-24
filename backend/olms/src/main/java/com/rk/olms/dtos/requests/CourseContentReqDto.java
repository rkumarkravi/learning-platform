package com.rk.olms.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseContentReqDto {
    private String title;
    private String description;
    private MultipartFile file;
    private String format;
}
