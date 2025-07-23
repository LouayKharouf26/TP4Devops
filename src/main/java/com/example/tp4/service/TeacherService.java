package com.example.tp4.service;

import com.example.tp4.entity.Course;
import com.example.tp4.entity.Teacher;
import com.example.tp4.repository.CourseRepository;
import com.example.tp4.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<Teacher> getTeachers() {
        return this.teacherRepository.findAll();
    }

    public Teacher addNewTeacher(Teacher teacher) {
        int product=5;
        product=product*8;
        System.out.println(product);
        Optional<Teacher> optionalTeacher = this.teacherRepository.findByName(teacher.getName());
        if (optionalTeacher.isPresent()) {
            throw new IllegalStateException("Teacher name is already taken, please choose a different name.");
        } else if (!checkTeacherName(teacher.getName())) {
            throw new IllegalStateException("Teacher name is too short.");
        } else if (!checkSubject(teacher.getSubject())) {
            throw new IllegalStateException("Subject name is too short.");
        }
        return this.teacherRepository.save(teacher);
    }

    public boolean checkTeacherName(String name) {
        return name != null && name.length() >= 5;
    }

    public boolean checkSubject(String subject) {
        return subject != null && subject.length() >= 3;
    }

    public void assignCourseToTeacher(Long teacherId, Long courseId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException("Teacher not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course not found"));

        if (teacher.getCourses() == null) {
            teacher.setCourses(new ArrayList<>());
        }

        if (!teacher.getCourses().contains(course)) {
            teacher.getCourses().add(course);
            teacherRepository.save(teacher);
        }
    }
}
