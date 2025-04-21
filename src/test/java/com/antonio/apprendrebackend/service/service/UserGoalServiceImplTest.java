package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.GoalNotCreatedException;
import com.antonio.apprendrebackend.service.model.UserGoal;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.GoalRepository;
import com.antonio.apprendrebackend.service.service.impl.UserGoalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserGoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private UserGoalServiceImpl userGoalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetActiveGoal() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        UserGoal activeGoal = new UserGoal();
        activeGoal.setId(1);
        activeGoal.setUserInfo(userInfo);
        activeGoal.setAttempts(10);
        activeGoal.setSuccessesAccuracy(0.8);
        activeGoal.setBeginDate(System.currentTimeMillis());

        // When
        when(goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo)).thenReturn(activeGoal);
        UserGoal result = userGoalService.getActiveGoal(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(10, result.getAttempts());
        assertEquals(0.8, result.getSuccessesAccuracy());
        verify(goalRepository, times(1)).findFirstByUserInfoOrderByBeginDateDesc(userInfo);
    }

    @Test
    void testGetActiveGoalNoGoalExists() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        // When
        when(goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo)).thenReturn(null);
        UserGoal result = userGoalService.getActiveGoal(userInfo);

        // Then
        assertNull(result);
        verify(goalRepository, times(1)).findFirstByUserInfoOrderByBeginDateDesc(userInfo);
    }

    @Test
    void testCreateGoalNewGoal() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        Integer attempts = 10;
        Double successesAccuracy = 0.8;

        UserGoal newGoal = new UserGoal(userInfo, attempts, successesAccuracy);
        newGoal.setId(1);

        // When
        when(goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo)).thenReturn(null);
        when(goalRepository.save(any(UserGoal.class))).thenReturn(newGoal);

        UserGoal result = userGoalService.createGoal(userInfo, attempts, successesAccuracy);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(10, result.getAttempts());
        assertEquals(0.8, result.getSuccessesAccuracy());
        verify(goalRepository, times(1)).findFirstByUserInfoOrderByBeginDateDesc(userInfo);
        verify(goalRepository, times(1)).save(any(UserGoal.class));
    }

    @Test
    void testCreateGoalWithExistingGoal() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        Integer attempts = 15;
        Double successesAccuracy = 0.9;

        UserGoal existingGoal = new UserGoal();
        existingGoal.setId(1);
        existingGoal.setUserInfo(userInfo);
        existingGoal.setAttempts(10);
        existingGoal.setSuccessesAccuracy(0.8);
        existingGoal.setBeginDate(System.currentTimeMillis() - 86400000); // 1 day ago

        UserGoal newGoal = new UserGoal(userInfo, attempts, successesAccuracy);
        newGoal.setId(2);

        // When
        when(goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo)).thenReturn(existingGoal);
        when(goalRepository.save(any(UserGoal.class))).thenReturn(newGoal);

        UserGoal result = userGoalService.createGoal(userInfo, attempts, successesAccuracy);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(15, result.getAttempts());
        assertEquals(0.9, result.getSuccessesAccuracy());
        verify(goalRepository, times(1)).findFirstByUserInfoOrderByBeginDateDesc(userInfo);
        verify(goalRepository, times(2)).save(any(UserGoal.class)); // Una vez para actualizar el existing y otra para el nuevo
    }

    @Test
    void testCreateGoalException() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        Integer attempts = 10;
        Double successesAccuracy = 0.8;

        // When
        when(goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo)).thenReturn(null);
        when(goalRepository.save(any(UserGoal.class))).thenReturn(null);

        // Then
        assertThrows(GoalNotCreatedException.class, () -> {
            userGoalService.createGoal(userInfo, attempts, successesAccuracy);
        });

        verify(goalRepository, times(1)).findFirstByUserInfoOrderByBeginDateDesc(userInfo);
        verify(goalRepository, times(1)).save(any(UserGoal.class));
    }
}
