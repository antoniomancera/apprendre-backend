package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.CourseDTO;
import com.antonio.apprendrebackend.service.exception.CourseNotFoundException;
import com.antonio.apprendrebackend.service.mapper.CourseMapper;
import com.antonio.apprendrebackend.service.model.Course;
import com.antonio.apprendrebackend.service.repository.CourseRepository;
import com.antonio.apprendrebackend.service.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCoursesSuccess() {
        // Given
        Course course1 = new Course();
        course1.setId(1);
        course1.setCode("JAVA101");

        Course course2 = new Course();
        course2.setId(2);
        course2.setCode("SPRING102");

        Course course3 = new Course();
        course3.setId(3);
        course3.setCode("REACT103");

        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setCode("JAVA101");

        CourseDTO courseDTO2 = new CourseDTO();
        courseDTO2.setCode("SPRING102");

        CourseDTO courseDTO3 = new CourseDTO();
        courseDTO3.setCode("REACT103");

        List<Course> courses = Arrays.asList(course1, course2, course3);

        // When
        when(courseRepository.findAll()).thenReturn(courses);
        when(courseMapper.toDTO(course1)).thenReturn(courseDTO1);
        when(courseMapper.toDTO(course2)).thenReturn(courseDTO2);
        when(courseMapper.toDTO(course3)).thenReturn(courseDTO3);

        List<CourseDTO> result = courseService.getAllCourses();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("JAVA101", result.get(0).getCode());
        assertEquals("SPRING102", result.get(1).getCode());
        assertEquals("REACT103", result.get(2).getCode());


        verify(courseRepository, times(1)).findAll();
        verify(courseMapper, times(1)).toDTO(course1);
        verify(courseMapper, times(1)).toDTO(course2);
        verify(courseMapper, times(1)).toDTO(course3);
    }

    @Test
    void testGetAllCoursesEmpty() {
        // Given
        List<Course> emptyCourses = Collections.emptyList();

        // When
        when(courseRepository.findAll()).thenReturn(emptyCourses);

        List<CourseDTO> result = courseService.getAllCourses();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());

        verify(courseRepository, times(1)).findAll();
        verifyNoInteractions(courseMapper);
    }

    @Test
    void testGetAllCoursesSingleCourse() {
        // Given
        Course singleCourse = new Course();
        singleCourse.setId(1);
        singleCourse.setCode("PYTHON101");

        CourseDTO singleCourseDTO = new CourseDTO();
        singleCourseDTO.setCode("PYTHON101");

        List<Course> courses = Arrays.asList(singleCourse);

        // When
        when(courseRepository.findAll()).thenReturn(courses);
        when(courseMapper.toDTO(singleCourse)).thenReturn(singleCourseDTO);

        List<CourseDTO> result = courseService.getAllCourses();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PYTHON101", result.get(0).getCode());

        verify(courseRepository, times(1)).findAll();
        verify(courseMapper, times(1)).toDTO(singleCourse);
    }

    @Test
    void testGetAllCoursesRepositoryCalledOnce() {
        // Given
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses = Arrays.asList(course1, course2);

        CourseDTO courseDTO1 = new CourseDTO();
        CourseDTO courseDTO2 = new CourseDTO();

        // When
        when(courseRepository.findAll()).thenReturn(courses);
        when(courseMapper.toDTO(course1)).thenReturn(courseDTO1);
        when(courseMapper.toDTO(course2)).thenReturn(courseDTO2);

        courseService.getAllCourses();

        // Then
        verify(courseRepository, times(1)).findAll();
        verifyNoMoreInteractions(courseRepository);
        verify(courseMapper, times(2)).toDTO(any(Course.class));
    }

    @Test
    void testGetCourseByCodeSuccess() {
        // Given
        String courseCode = "JAVA101";
        Course course = new Course();
        course.setId(1);
        course.setCode(courseCode);

        // When
        when(courseRepository.findByCode(courseCode)).thenReturn(Optional.of(course));

        Course result = courseService.getCourseByCode(courseCode);

        // Then
        assertNotNull(result);
        assertEquals(courseCode, result.getCode());
        assertEquals(1, result.getId());

        verify(courseRepository, times(1)).findByCode(courseCode);
    }

    @Test
    void testGetCourseByCodeNotFound() {
        // Given
        String courseCode = "NONEXISTENT";

        // When
        when(courseRepository.findByCode(courseCode)).thenReturn(Optional.empty());

        // Then
        CourseNotFoundException exception = assertThrows(
                CourseNotFoundException.class,
                () -> courseService.getCourseByCode(courseCode)
        );

        assertTrue(exception.getMessage().contains("Course-"));
        assertTrue(exception.getMessage().contains("not found"));

        verify(courseRepository, times(1)).findByCode(courseCode);
    }

    @Test
    void testGetCourseByCodeNullCode() {
        // Given
        String nullCode = null;

        // When
        when(courseRepository.findByCode(nullCode)).thenReturn(Optional.empty());

        // Then
        CourseNotFoundException exception = assertThrows(
                CourseNotFoundException.class,
                () -> courseService.getCourseByCode(nullCode)
        );

        assertNotNull(exception.getMessage());

        verify(courseRepository, times(1)).findByCode(nullCode);
    }

    @Test
    void testGetCourseByCodeEmptyCode() {
        // Given
        String emptyCode = "";

        // When
        when(courseRepository.findByCode(emptyCode)).thenReturn(Optional.empty());

        // Then
        CourseNotFoundException exception = assertThrows(
                CourseNotFoundException.class,
                () -> courseService.getCourseByCode(emptyCode)
        );

        assertNotNull(exception.getMessage());

        verify(courseRepository, times(1)).findByCode(emptyCode);
    }

    @Test
    void testGetCourseByCodeRepositoryCalledOnce() {
        // Given
        String courseCode = "TEST123";
        Course course = new Course();
        course.setCode(courseCode);

        // When
        when(courseRepository.findByCode(courseCode)).thenReturn(Optional.of(course));

        courseService.getCourseByCode(courseCode);

        // Then
        verify(courseRepository, times(1)).findByCode(courseCode);
        verifyNoMoreInteractions(courseRepository);
    }
}
