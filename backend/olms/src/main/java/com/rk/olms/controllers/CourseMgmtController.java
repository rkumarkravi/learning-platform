package com.rk.olms.controllers;

import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.CourseReqDto;
import com.rk.olms.dtos.responses.CourseResDto;
import com.rk.olms.services.CourseMgmtService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course-mgmt")
public class CourseMgmtController {
    private final CourseMgmtService courseMgmtService;

    public CourseMgmtController(CourseMgmtService courseMgmtService) {
        this.courseMgmtService = courseMgmtService;
    }

    @RequestMapping("/create")
    public ResponseDto<CourseResDto> createCourse(@RequestBody CourseReqDto courseReqDto){
        return courseMgmtService.createCourseMetaData(courseReqDto);
    }

}
