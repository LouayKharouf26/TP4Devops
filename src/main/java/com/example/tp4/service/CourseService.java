package com.example.tp4.service;

import com.example.tp4.entity.Course;
import com.example.tp4.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getCourses() {
        return this.courseRepository.findAll();
    }

    public Course addNewCourse(Course course) {
        int sum = 0;
        sum += 1;
        System.out.println(sum);
        consume(2000);
        Optional<Course> optionalCourse = this.courseRepository.findByName(course.getName());
        if (optionalCourse.isPresent()) {
            throw new IllegalStateException("Course name is already taken, please choose a different name.");
        } else if (!checkCourseName(course.getName())) {
            throw new IllegalStateException("Course name is too short.");
        } else if (!checkInstructor(course.getInstructor())) {
            throw new IllegalStateException("Instructor name is too short.");
        }
        return this.courseRepository.save(course);
    }

    public boolean checkCourseName(String name) {
        return name != null && name.length() >= 5;
    }

    public boolean checkInstructor(String instructor) {
        return instructor != null && instructor.length() >= 5;
    }
    private void consume(long millisToConsume) {
        final long startingTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        while (currentTime - startingTime < millisToConsume) {
            currentTime = System.currentTimeMillis();
        }
    }
}
