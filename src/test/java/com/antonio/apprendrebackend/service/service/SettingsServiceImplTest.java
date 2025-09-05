package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.CourseDTO;
import com.antonio.apprendrebackend.service.dto.SettingsOptionsDTO;
import com.antonio.apprendrebackend.service.service.impl.SettingsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SettingsServiceImplTest {
    @Mock
    private CourseService courseService;

    @InjectMocks
    private SettingsServiceImpl settingsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSettingsOptionsSuccess() {
        // Given
        CourseDTO course1 = new CourseDTO();
        course1.setCode("ES-FR");

        CourseDTO course2 = new CourseDTO();
        course2.setCode("ES-CH");

        CourseDTO course3 = new CourseDTO();
        course3.setCode("ES-EN");

        List<CourseDTO> courses = Arrays.asList(course1, course2, course3);

        // When
        when(courseService.getAllCourses()).thenReturn(courses);

        SettingsOptionsDTO result = settingsService.getSettingsOptions();

        // Then
        assertNotNull(result);
        assertNotNull(result.getCourses());
        assertEquals(3, result.getCourses().size());

        assertEquals("ES-FR", result.getCourses().get(0).getCode());
        assertEquals("ES-CH", result.getCourses().get(1).getCode());
        assertEquals("ES-EN", result.getCourses().get(2).getCode());

        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetSettingsOptionsWithEmptyCourses() {
        // Given
        List<CourseDTO> emptyCourses = Collections.emptyList();

        // When
        when(courseService.getAllCourses()).thenReturn(emptyCourses);

        SettingsOptionsDTO result = settingsService.getSettingsOptions();

        // Then
        assertNotNull(result);
        assertNotNull(result.getCourses());
        assertTrue(result.getCourses().isEmpty());
        assertEquals(0, result.getCourses().size());

        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetSettingsOptionsWithSingleCourse() {
        // Given
        CourseDTO singleCourse = new CourseDTO();
        singleCourse.setCode("ES-FR");

        List<CourseDTO> courses = Arrays.asList(singleCourse);

        // When
        when(courseService.getAllCourses()).thenReturn(courses);

        SettingsOptionsDTO result = settingsService.getSettingsOptions();

        // Then
        assertNotNull(result);
        assertNotNull(result.getCourses());
        assertEquals(1, result.getCourses().size());
        assertEquals("ES-FR", result.getCourses().get(0).getCode());

        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetSettingsOptionsWithNullCourses() {
        // Given / When
        when(courseService.getAllCourses()).thenReturn(null);

        SettingsOptionsDTO result = settingsService.getSettingsOptions();

        // Then
        assertNotNull(result);
        assertNull(result.getCourses());

        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetSettingsOptionsCreatesNewSettingsObject() {
        // Given
        List<CourseDTO> courses = Arrays.asList(new CourseDTO(), new CourseDTO());

        // When
        when(courseService.getAllCourses()).thenReturn(courses);

        SettingsOptionsDTO result1 = settingsService.getSettingsOptions();
        SettingsOptionsDTO result2 = settingsService.getSettingsOptions();

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2);
        assertEquals(result1.getCourses().size(), result2.getCourses().size());

        verify(courseService, times(2)).getAllCourses();
    }

    @Test
    void testGetSettingsOptionsCourseServiceCalledOnce() {
        // Given
        List<CourseDTO> courses = Arrays.asList(new CourseDTO());

        // When
        when(courseService.getAllCourses()).thenReturn(courses);

        settingsService.getSettingsOptions();

        // Then
        verify(courseService, times(1)).getAllCourses();
        verifyNoMoreInteractions(courseService);
    }

    @Test
    void testGetSettingsOptionsReturnsCorrectType() {
        // Given
        List<CourseDTO> courses = Arrays.asList(new CourseDTO());

        // When
        when(courseService.getAllCourses()).thenReturn(courses);

        SettingsOptionsDTO result = settingsService.getSettingsOptions();

        // Then
        assertNotNull(result);
        assertInstanceOf(SettingsOptionsDTO.class, result);
        assertInstanceOf(List.class, result.getCourses());

        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetSettingsOptionsHandlesCoursesListCorrectly() {
        // Given
        CourseDTO course = new CourseDTO();
        course.setCode("ES-FR");

        List<CourseDTO> courses = Arrays.asList(course);

        // When
        when(courseService.getAllCourses()).thenReturn(courses);

        SettingsOptionsDTO result = settingsService.getSettingsOptions();

        // Then
        assertNotNull(result);
        assertEquals(courses, result.getCourses());
        assertSame(courses, result.getCourses());
        assertEquals("ES-FR", result.getCourses().get(0).getCode());

        verify(courseService, times(1)).getAllCourses();
    }
}
