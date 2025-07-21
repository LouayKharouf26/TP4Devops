package com.example.tp4;

import com.example.tp4.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseServiceTest {

    @Mock
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckCourseNameMethod() {
        Mockito.when(courseService.checkCourseName("Cloud Computing")).thenReturn(true);
        assertEquals(true, courseService.checkCourseName("Cloud Computing"));
    }

    @Test
    public void testCheckInstructorMethod() {
        Mockito.when(courseService.checkInstructor("Ali")).thenReturn(false);
        assertEquals(false, courseService.checkInstructor("Ali"));
    }
}
