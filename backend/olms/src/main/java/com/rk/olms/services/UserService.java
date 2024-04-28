package com.rk.olms.services;

import com.rk.olms.configs.security.SecurityUserDetails;
import com.rk.olms.daos.repos.CourseRepository;
import com.rk.olms.daos.repos.StudentEnrolledRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.responses.CourseResDto;
import com.rk.olms.dtos.responses.TeacherInitResponseDto;
import com.rk.olms.enums.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final CourseRepository courseRepository;
    private final StudentEnrolledRepository studentEnrolledRepository;

    public UserService(CourseRepository courseRepository, StudentEnrolledRepository studentEnrolledRepository) {
        this.courseRepository = courseRepository;
        this.studentEnrolledRepository = studentEnrolledRepository;
    }

    public Object init() {
        ResponseDto<List<?>> responseDtoElse = new ResponseDto<>();
        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role role = userDetails.getUserEntity().getRole();
        if (role == Role.TEACHER) {
            ResponseDto<List<?>> responseDto = new ResponseDto<>();
            List<TeacherInitResponseDto> courses = courseRepository
                    .findByUserEntity_IdOrderByLikesDesc(userDetails.getUserEntity().getId())
                    .stream().map(x ->
                            TeacherInitResponseDto.builder()
                                    .cId(x.getCourseId())
                                    .author(x.getAuthor())
                                    .likes(x.getLikes())
                                    .title(x.getTitle())
                                    .build()
                    ).collect(Collectors.toList());
            responseDto.setPayload(courses);
            responseDto.setRs("S");
            responseDto.setRd("SUCCESS");
            return responseDto;
        } else if (role == Role.STUDENT) {
            ResponseDto<Map<String, List<CourseResDto>>> responseDto = new ResponseDto<>();
            Map<String, List<CourseResDto>> map = new HashMap<>();

            List<CourseResDto> enrolledCourses = studentEnrolledRepository.findByPaymentEntity_TransactionIdNotNullOrderByEnrollmentDtDesc()
                    .stream()
                    .map(x -> new CourseResDto(x.getCourseEntity()))
                    .collect(Collectors.toList());
            map.put("Enrolled Courses", enrolledCourses);

            responseDto.setPayload(map);
            responseDto.setRs("S");
            responseDto.setRd("SUCCESS");
            return responseDto;
        }
        return responseDtoElse;
    }
}
