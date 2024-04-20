package com.rk.olms.services;

import com.rk.olms.daos.models.CourseEntity;
import com.rk.olms.daos.repos.CourseRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.responses.CourseResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public ResponseDto<List<CourseResDto>> getCourses() {
        ResponseDto<List<CourseResDto>> responseDto = new ResponseDto<>();

        List<CourseResDto> courseList = courseRepository.findAll().stream().map(CourseResDto::new).collect(Collectors.toList());

        responseDto.setRs("S");
        responseDto.setRd("Successful");
        responseDto.setPayload(courseList);
        return responseDto;
    }

    public ResponseDto<CourseResDto> getCourse(long courseId) {
        ResponseDto<CourseResDto> responseDto = new ResponseDto<>();
        Optional<CourseEntity> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            CourseResDto courseDto = new CourseResDto(courseOptional.get());
            responseDto.setRs("S");
            responseDto.setRd("Successful");
            responseDto.setPayload(courseDto);
        } else {
            responseDto.setRd("Invalid CourseId!");
        }
        return responseDto;
    }
}
