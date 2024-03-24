package com.rk.olms.controllers;

import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.CourseContentReqDto;
import com.rk.olms.dtos.requests.CourseReqDto;
import com.rk.olms.dtos.responses.CourseContentResDto;
import com.rk.olms.dtos.responses.CourseResDto;
import com.rk.olms.services.CourseMgmtService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course-mgmt")
public class CourseMgmtController {
    private final CourseMgmtService courseMgmtService;

    public CourseMgmtController(CourseMgmtService courseMgmtService) {
        this.courseMgmtService = courseMgmtService;
    }

    @PostMapping("/create")
    public ResponseDto<CourseResDto> createCourse(@RequestBody CourseReqDto courseReqDto) {
        return courseMgmtService.createCourseMetaData(courseReqDto);
    }

    @PostMapping("/content/{courseId}")
    public ResponseDto<CourseContentResDto> createCourse(@ModelAttribute CourseContentReqDto courseContentReqDto, @PathVariable("courseId") Long courseId) {
        return courseMgmtService.createCourseContent(courseContentReqDto, courseId);
    }

}
