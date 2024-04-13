package com.rk.olms.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherInitResponseDto {
    private long cId;
    private String author;
    private String title;
    private int likes;
    private boolean certificationAvailable;
}
