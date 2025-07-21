package com.example.tp4.controller;

import com.example.tp4.entity.Teacher;
import com.example.tp4.service.TeacherService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private final TeacherService teacherService;

    public String result = "hello world";

    // Get all teachers
    @GetMapping
    public List<Teacher> getAllTeachers() {
        return this.teacherService.getTeachers();
    }

    // Hello world endpoint
    @GetMapping("/")
    public String helloWorld() {
        return "hello world from teachers!";
    }

    // Add a new teacher
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addTeacher")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return this.teacherService.addNewTeacher(teacher);
    }

    // Assign course to teacher
    @PutMapping("/{teacherId}/courses/{courseId}")
    public ResponseEntity<String> assignCourseToTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long courseId) {
        teacherService.assignCourseToTeacher(teacherId, courseId);
        return ResponseEntity.ok("Course assigned to teacher successfully.");
    }
}
