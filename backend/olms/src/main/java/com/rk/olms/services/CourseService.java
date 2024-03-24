package com.rk.olms.services;

import com.rk.olms.daos.repos.CourseEntityRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.responses.CourseResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseEntityRepository courseRepository;

    public CourseService(CourseEntityRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public ResponseDto<List<CourseResDto>> getCourses(){
        ResponseDto<List<CourseResDto>> responseDto=new ResponseDto<>();

        List<CourseResDto> courseList = courseRepository.findAll().stream().map(CourseResDto::new).collect(Collectors.toList());

        responseDto.setRs("S");
        responseDto.setRd("Course created successfully!");
        responseDto.setPayload(courseList);
        return responseDto;
    }
}
