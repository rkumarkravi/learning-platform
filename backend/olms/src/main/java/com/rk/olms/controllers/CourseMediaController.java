package com.rk.olms.controllers;

import com.rk.olms.services.CourseMediaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media")
public class CourseMediaController {

    private final CourseMediaService courseMediaService;

    public CourseMediaController(CourseMediaService courseMediaService) {
        this.courseMediaService = courseMediaService;
    }

    @GetMapping("/{contentId}")
    public Object streamVideo(@PathVariable String contentId,
                              @RequestHeader(value = "Range", required = false) String rangeHeader,
                              HttpServletRequest request) {
        return courseMediaService.getContent(Long.valueOf(contentId), request);
    }
}
