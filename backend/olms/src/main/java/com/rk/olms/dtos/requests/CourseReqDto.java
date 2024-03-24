package com.rk.olms.dtos.requests;

import com.rk.olms.enums.Audience;
import com.rk.olms.enums.ProficiencyLevel;
import lombok.Data;

import java.util.List;

@Data
public class CourseReqDto {
    private String cId;
    private String title;
    private String description;
    private String author;
    private int durationInHours;
    private ProficiencyLevel level;
    private List<String> prerequisites;
    private String learningObjectives;
    private List<String> topics;
    private String format;
    private String language;
    private List<String> keywords;
    private Audience audience;
    private boolean certificationAvailable;
    private String version;
}
