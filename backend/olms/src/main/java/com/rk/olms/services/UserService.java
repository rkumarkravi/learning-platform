package com.rk.olms.services;

import com.rk.olms.configs.security.SecurityUserDetails;
import com.rk.olms.daos.repos.CourseRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.responses.TeacherInitResponseDto;
import com.rk.olms.enums.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final CourseRepository courseRepository;

    public UserService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Object init() {
        ResponseDto<List<?>> responseDto = new ResponseDto<>();
        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role role = userDetails.getUserEntity().getRole();
        if (role == Role.TEACHER) {
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
        } else if (role == Role.STUDENT) {

        }
        return responseDto;
    }
}
