package com.example.tp4.controller;

import com.example.tp4.entity.Course;
import com.example.tp4.service.CourseService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private final CourseService courseService;

    public String result = "hello world";

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return this.courseService.getCourses();
    }

    // Hello world endpoint
    @GetMapping("/")
    public String helloWorld() {
        return "hello world !";
    }

    // Add a new course
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addCourse")
    public Course addCourse(@RequestBody Course course) {
        return this.courseService.addNewCourse(course);
    }
}
