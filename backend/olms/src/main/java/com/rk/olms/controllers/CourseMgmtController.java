package com.rk.olms.controllers;

import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.CourseContentReqDto;
import com.rk.olms.dtos.requests.CourseReqDto;
import com.rk.olms.dtos.responses.CourseContentResDto;
import com.rk.olms.dtos.responses.CourseResDto;
import com.rk.olms.services.CourseMgmtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/content/{courseId}/{contentId}")
    public ResponseDto<CourseContentResDto> updateCourse(@ModelAttribute CourseContentReqDto courseContentReqDto,
                                                         @PathVariable("courseId") Long courseId,
                                                         @PathVariable("contentId") Long contentId) {
        return courseMgmtService.updateCourseContent(courseContentReqDto, courseId, contentId);
    }

    @DeleteMapping("/content/{courseId}/{contentId}")
    public ResponseDto<CourseContentResDto> deleteCourse(@PathVariable("courseId") Long courseId,
                                                         @PathVariable("contentId") Long contentId) {
        return courseMgmtService.deleteCourseContent(courseId, contentId);
    }

    @GetMapping("/content/{courseId}")
    public ResponseDto<List<CourseContentResDto>> getAllCourseContent(@PathVariable("courseId") Long courseId) {
        return courseMgmtService.getAllCourseContent(courseId);
    }
}
