package com.rk.olms.dtos.responses;

import com.rk.olms.daos.models.CourseEntity;
import com.rk.olms.dtos.requests.CourseReqDto;
import com.rk.olms.enums.Audience;
import com.rk.olms.enums.ProficiencyLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class CourseResDto {
    private Long cId;
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

    public CourseResDto(CourseEntity ent) {
        this.setCId(ent.getCourseId());
        this.setTitle(ent.getTitle());
        this.setDescription(ent.getDescription());
        this.setAuthor(ent.getAuthor());
        this.setDurationInHours(ent.getDurationInHours());
        this.setLevel(ent.getLevel());
        this.setPrerequisites(Arrays.asList(ent.getPrerequisites().split(",")));
        this.setLearningObjectives(ent.getLearningObjectives());
        this.setTopics(Arrays.asList(ent.getTopics().split(",")));
        this.setFormat(ent.getFormat());
        this.setLanguage(ent.getLanguage());
        this.setKeywords(Arrays.asList(ent.getKeywords().split(",")));
        this.setAudience(ent.getAudience());
        this.setCertificationAvailable(ent.isCertificationAvailable());
        this.setVersion(ent.getVersion());
    }
}
