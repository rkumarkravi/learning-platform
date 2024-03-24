package com.rk.olms.controllers;

import com.rk.olms.services.CourseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @PostMapping("")
    public Object getCourses() {
        return this.courseService.getCourses();
    }

    @PostMapping("/{cid}")
    public Object getCourse(@PathVariable("cid") long courseId) {
        return this.courseService.getCourse(courseId);
    }

}
