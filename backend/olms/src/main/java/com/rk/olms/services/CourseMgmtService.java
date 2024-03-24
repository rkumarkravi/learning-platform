package com.rk.olms.services;

import com.rk.olms.daos.models.CourseContentEntity;
import com.rk.olms.daos.models.CourseEntity;
import com.rk.olms.daos.repos.CourseContentRepository;
import com.rk.olms.daos.repos.CourseRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.CourseContentReqDto;
import com.rk.olms.dtos.requests.CourseReqDto;
import com.rk.olms.dtos.responses.CourseContentResDto;
import com.rk.olms.dtos.responses.CourseResDto;
import com.rk.olms.utils.FileUploadUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseMgmtService {
    private final CourseRepository courseRepository;
    @Value("${file.upload.dir}")
    String fileUploadDir;
    private final CourseContentRepository courseContentRepository;

    public CourseMgmtService(CourseRepository courseRepository, CourseContentRepository courseContentRepository) {
        this.courseRepository = courseRepository;
        this.courseContentRepository = courseContentRepository;
    }

    public ResponseDto<CourseResDto> createCourseMetaData(CourseReqDto courseReqDto) {
        ResponseDto<CourseResDto> responseDto = new ResponseDto<>();
        CourseEntity courseEntity = new CourseEntity(courseReqDto);
        courseEntity = courseRepository.save(courseEntity);

        responseDto.setRs("S");
        responseDto.setRd("Course created successfully!");
        responseDto.setPayload(new CourseResDto(courseEntity));
        return responseDto;
    }

    public ResponseDto<CourseContentResDto> createCourseContent(CourseContentReqDto courseContentReqDto,
                                                                long courseId) {
        ResponseDto<CourseContentResDto> responseDto = new ResponseDto<>();

        Optional<CourseEntity> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isPresent()) {
            String uploadDir = fileUploadDir + courseId;
            ResponseDto<String> uploadRes = FileUploadUtility.uploadToDir(uploadDir, courseContentReqDto.getFile());

            if ("S".equals(uploadRes.getRs())) {
                CourseContentEntity cce = CourseContentEntity
                        .builder()
                        .title(courseContentReqDto.getTitle())
                        .description(courseContentReqDto.getDescription())
                        .format(courseContentReqDto.getFormat())
                        .contentUrl(uploadRes.getPayload())
                        .title(courseContentReqDto.getTitle())
                        .course(courseOptional.get())
                        .build();

                cce = courseContentRepository.save(cce);

                responseDto.setPayload(CourseContentResDto.builder()
                        .contentId(cce.getId())
                        .courseId(cce.getCourse().getCourseId())
                        .title(cce.getTitle())
                        .description(cce.getDescription())
                        .url(cce.getContentUrl())
                        .format(cce.getFormat())
                        .build()
                );
                responseDto.setRd("Content uploaded successfully!");
                responseDto.setRs("S");
            } else {
                responseDto.setRd(uploadRes.getRd());
                responseDto.setRs(uploadRes.getRs());
            }
        }
        return responseDto;
    }
}
