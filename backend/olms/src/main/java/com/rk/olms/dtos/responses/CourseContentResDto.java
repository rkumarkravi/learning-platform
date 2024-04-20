package com.rk.olms.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseContentResDto {
    private long contentId;
    private long courseId;
    private String title;
    private String description;
    private String url;
    private String format;
}
