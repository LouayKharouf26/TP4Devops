package com.example.tp4;

import com.example.tp4.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeacherServiceTest {

    @Mock
    private TeacherService teacherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckTeacherNameMethod() {
        Mockito.when(teacherService.checkTeacherName("James")).thenReturn(true);
        Mockito.when(teacherService.checkTeacherName("Jo")).thenReturn(false);

        assertEquals(true, teacherService.checkTeacherName("James"));
        assertEquals(false, teacherService.checkTeacherName("Jo"));
    }

    @Test
    public void testCheckSubjectMethod() {
        Mockito.when(teacherService.checkSubject("Math")).thenReturn(true);
        Mockito.when(teacherService.checkSubject("A")).thenReturn(false);

        assertEquals(true, teacherService.checkSubject("Math"));
        assertEquals(false, teacherService.checkSubject("A"));
    }
}
