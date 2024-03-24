package com.rk.olms.daos.models;

import com.rk.olms.dtos.requests.CourseReqDto;
import com.rk.olms.enums.Audience;
import com.rk.olms.enums.ProficiencyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "lms_courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String title;
    private String description;
    private String author;
    private int durationInHours;
    @Enumerated(EnumType.STRING)
    @Column(name="c_level")
    private ProficiencyLevel level;
    private String prerequisites;
    private String learningObjectives;
    private String topics;
    private String format;
    private String language;
    private String keywords;
    @Enumerated(EnumType.STRING)
    private Audience audience;
    private boolean certificationAvailable;
    private String version;
    private int likes=0;

    public CourseEntity(CourseReqDto dto) {
        this.setTitle(dto.getTitle());
        this.setDescription(dto.getDescription());
        this.setAuthor(dto.getAuthor());
        this.setDurationInHours(dto.getDurationInHours());
        this.setLevel(dto.getLevel());
        this.setPrerequisites(String.join(",",dto.getPrerequisites()));
        this.setLearningObjectives(dto.getLearningObjectives());
        this.setTopics(String.join(",",dto.getTopics()));
        this.setFormat(dto.getFormat());
        this.setLanguage(dto.getLanguage());
        this.setKeywords(String.join(",",dto.getKeywords()));
        this.setAudience(dto.getAudience());
        this.setCertificationAvailable(dto.isCertificationAvailable());
        this.setVersion(dto.getVersion());
    }
}
