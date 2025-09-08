package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.Course;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.UserInfoRepository;
import com.antonio.apprendrebackend.service.service.impl.UserInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class UserInfoServiceImplTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindBySupabaseIdSuccess() {
        // Given
        String supabaseId = "test-supabase-id-123";
        UserInfo mockUserInfo = new UserInfo();
        mockUserInfo.setId(1);
        mockUserInfo.setSupabaseId(supabaseId);

        // When
        when(userInfoRepository.findBySupabaseId(supabaseId)).thenReturn(Optional.of(mockUserInfo));

        UserInfo result = userInfoService.findBySupabaseId(supabaseId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(supabaseId, result.getSupabaseId());

        verify(userInfoRepository, times(1)).findBySupabaseId(supabaseId);
    }

    @Test
    void testFindBySupabaseIdNotFound() {
        // Given
        String supabaseId = "non-existent-id";

        // When
        when(userInfoRepository.findBySupabaseId(supabaseId)).thenReturn(Optional.empty());

        // Then
        UserInfoNotFoundException exception = assertThrows(
                UserInfoNotFoundException.class,
                () -> userInfoService.findBySupabaseId(supabaseId)
        );

        assertEquals("Not found userInfo", exception.getMessage());
        verify(userInfoRepository, times(1)).findBySupabaseId(supabaseId);
    }

    @Test
    void testFindBySupabaseIdWithNullId() {
        // Given
        String nullId = null;

        // When
        when(userInfoRepository.findBySupabaseId(nullId)).thenReturn(Optional.empty());

        // Then
        UserInfoNotFoundException exception = assertThrows(
                UserInfoNotFoundException.class,
                () -> userInfoService.findBySupabaseId(nullId)
        );

        assertEquals("Not found userInfo", exception.getMessage());
        verify(userInfoRepository, times(1)).findBySupabaseId(nullId);
    }

    @Test
    void testFindBySupabaseIdWithEmptyId() {
        // Given
        String emptyId = "";

        // When
        when(userInfoRepository.findBySupabaseId(emptyId)).thenReturn(Optional.empty());

        // Then
        UserInfoNotFoundException exception = assertThrows(
                UserInfoNotFoundException.class,
                () -> userInfoService.findBySupabaseId(emptyId)
        );

        assertEquals("Not found userInfo", exception.getMessage());
        verify(userInfoRepository, times(1)).findBySupabaseId(emptyId);
    }

    @Test
    void testFindBySupabaseIdRepositoryCalledOnce() {
        // Given
        String supabaseId = "test-id";
        UserInfo mockUserInfo = new UserInfo();
        mockUserInfo.setSupabaseId(supabaseId);

        // When
        when(userInfoRepository.findBySupabaseId(supabaseId)).thenReturn(Optional.of(mockUserInfo));

        userInfoService.findBySupabaseId(supabaseId);

        // Then
        verify(userInfoRepository, times(1)).findBySupabaseId(supabaseId);
        verifyNoMoreInteractions(userInfoRepository);
    }


    @Test
    void testSetUserInfoCurrentCourseSuccess() {
        // Given
        String courseCode = "EN-ES";
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);

        Course course = new Course();
        course.setId(1);
        course.setCode(courseCode);

        UserInfo updatedUserInfo = new UserInfo();
        updatedUserInfo.setId(1);
        updatedUserInfo.setCurrentCourse(course);

        // When
        when(courseService.getCourseByCode(courseCode)).thenReturn(course);
        when(userInfoRepository.save(userInfo)).thenReturn(updatedUserInfo);

        UserInfo result = userInfoService.setUserInfoCurrentCourse(courseCode, userInfo);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertNotNull(result.getCurrentCourse());
        assertEquals(courseCode, result.getCurrentCourse().getCode());

        verify(courseService, times(1)).getCourseByCode(courseCode);
        verify(userInfoRepository, times(1)).save(userInfo);
    }

    @Test
    void testSetUserInfoCurrentCourseUpdatesUserInfoCourse() {
        // Given
        String courseCode = "SPRING102";
        UserInfo userInfo = new UserInfo();
        userInfo.setId(2);
        userInfo.setCurrentCourse(null);

        Course newCourse = new Course();
        newCourse.setId(2);
        newCourse.setCode(courseCode);

        UserInfo savedUserInfo = new UserInfo();
        savedUserInfo.setId(2);
        savedUserInfo.setCurrentCourse(newCourse);

        // When
        when(courseService.getCourseByCode(courseCode)).thenReturn(newCourse);
        when(userInfoRepository.save(userInfo)).thenReturn(savedUserInfo);

        userInfoService.setUserInfoCurrentCourse(courseCode, userInfo);

        // Then
        assertEquals(newCourse, userInfo.getCurrentCourse());

        verify(courseService, times(1)).getCourseByCode(courseCode);
        verify(userInfoRepository, times(1)).save(userInfo);
    }

    @Test
    void testSetUserInfoCurrentCourseWithExistingCourse() {
        // Given
        String oldCourseCode = "REACT103";
        String newCourseCode = "VUE104";

        Course oldCourse = new Course();
        oldCourse.setId(3);
        oldCourse.setCode(oldCourseCode);

        Course newCourse = new Course();
        newCourse.setId(4);
        newCourse.setCode(newCourseCode);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setCurrentCourse(oldCourse);

        UserInfo savedUserInfo = new UserInfo();
        savedUserInfo.setId(1);
        savedUserInfo.setCurrentCourse(newCourse);

        // When
        when(courseService.getCourseByCode(newCourseCode)).thenReturn(newCourse);
        when(userInfoRepository.save(userInfo)).thenReturn(savedUserInfo);

        UserInfo result = userInfoService.setUserInfoCurrentCourse(newCourseCode, userInfo);

        // Then
        assertNotNull(result);
        assertEquals(newCourse, result.getCurrentCourse());
        assertEquals(newCourseCode, result.getCurrentCourse().getCode());
        assertEquals(newCourse, userInfo.getCurrentCourse());

        verify(courseService, times(1)).getCourseByCode(newCourseCode);
        verify(userInfoRepository, times(1)).save(userInfo);
    }

    @Test
    void testSetUserInfoCurrentCourseWithNullCourseCode() {
        // Given
        String nullCourseCode = null;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);

        // When / Then
        when(courseService.getCourseByCode(nullCourseCode)).thenThrow(new RuntimeException("Course code cannot be null"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userInfoService.setUserInfoCurrentCourse(nullCourseCode, userInfo)
        );

        assertEquals("Course code cannot be null", exception.getMessage());
        verify(courseService, times(1)).getCourseByCode(nullCourseCode);
        verify(userInfoRepository, never()).save(any());
    }

    @Test
    void testSetUserInfoCurrentCourseWithNullUserInfo() {
        // Given
        String courseCode = "TEST101";
        UserInfo nullUserInfo = null;

        Course course = new Course();
        course.setCode(courseCode);

        // When
        when(courseService.getCourseByCode(courseCode)).thenReturn(course);

        // Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> userInfoService.setUserInfoCurrentCourse(courseCode, nullUserInfo)
        );

        verify(courseService, times(1)).getCourseByCode(courseCode);
        verify(userInfoRepository, never()).save(any());
    }

    @Test
    void testSetUserInfoCurrentCourseServicesCalledCorrectly() {
        // Given
        String courseCode = "TESTING123";
        UserInfo userInfo = new UserInfo();
        Course course = new Course();
        course.setCode(courseCode);
        UserInfo savedUserInfo = new UserInfo();

        // When
        when(courseService.getCourseByCode(courseCode)).thenReturn(course);
        when(userInfoRepository.save(userInfo)).thenReturn(savedUserInfo);

        userInfoService.setUserInfoCurrentCourse(courseCode, userInfo);

        // Then
        verify(courseService, times(1)).getCourseByCode(courseCode);
        verify(userInfoRepository, times(1)).save(userInfo);
        verifyNoMoreInteractions(courseService);
        verifyNoMoreInteractions(userInfoRepository);
    }

    @Test
    void testSetUserInfoCurrentCourseReturnsRepositoryResult() {
        // Given
        String courseCode = "RETURN_TEST";
        UserInfo inputUserInfo = new UserInfo();
        inputUserInfo.setId(1);

        Course course = new Course();
        course.setCode(courseCode);

        UserInfo repositoryResult = new UserInfo();
        repositoryResult.setId(1);
        repositoryResult.setCurrentCourse(course);

        // When
        when(courseService.getCourseByCode(courseCode)).thenReturn(course);
        when(userInfoRepository.save(inputUserInfo)).thenReturn(repositoryResult);

        UserInfo result = userInfoService.setUserInfoCurrentCourse(courseCode, inputUserInfo);

        // Then
        assertSame(repositoryResult, result);
        assertEquals(course, result.getCurrentCourse());

        verify(courseService, times(1)).getCourseByCode(courseCode);
        verify(userInfoRepository, times(1)).save(inputUserInfo);
    }
}
