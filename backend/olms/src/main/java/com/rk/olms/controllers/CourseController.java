package com.rk.olms.controllers;

import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.responses.CourseContentResDto;
import com.rk.olms.services.CourseMgmtService;
import com.rk.olms.services.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseMgmtService courseMgmtService;

    public CourseController(CourseService courseService, CourseMgmtService courseMgmtService) {
        this.courseService = courseService;
        this.courseMgmtService = courseMgmtService;
    }


    @PostMapping("")
    public Object getCourses() {
        return this.courseService.getCourses();
    }

    @PostMapping("/{cid}")
    public Object getCourse(@PathVariable("cid") long courseId) {
        return this.courseService.getCourse(courseId);
    }

    @GetMapping("/content/{courseId}")
    public ResponseDto<List<CourseContentResDto>> getAllCourseContent(@PathVariable("courseId") Long courseId) {
        return courseMgmtService.getAllCourseContent(courseId);
    }
}
