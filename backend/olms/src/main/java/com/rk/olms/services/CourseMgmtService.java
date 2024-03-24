package com.rk.olms.services;

import com.rk.olms.daos.models.CourseEntity;
import com.rk.olms.daos.repos.CourseEntityRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.CourseReqDto;
import com.rk.olms.dtos.responses.CourseResDto;
import org.springframework.stereotype.Service;

@Service
public class CourseMgmtService {
    private final CourseEntityRepository courseRepository;

    public CourseMgmtService(CourseEntityRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public ResponseDto<CourseResDto> createCourseMetaData(CourseReqDto courseReqDto) {
        ResponseDto<CourseResDto> responseDto=new ResponseDto<>();
        CourseEntity courseEntity = new CourseEntity(courseReqDto);
        courseEntity=courseRepository.save(courseEntity);

        responseDto.setRs("S");
        responseDto.setRd("Course created successfully!");
        responseDto.setPayload(new CourseResDto(courseEntity));
        return responseDto;
    }
}
