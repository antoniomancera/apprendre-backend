package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.GoalNotCreatedException;
import com.antonio.apprendrebackend.service.model.Goal;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.GoalRepository;
import com.antonio.apprendrebackend.service.service.impl.GoalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalServiceImpl goalService;

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

        Goal activeGoal = new Goal();
        activeGoal.setId(1);
        activeGoal.setUserInfo(userInfo);
        activeGoal.setAttempts(10);
        activeGoal.setSuccessesAccuracy(0.8);
        activeGoal.setBeginDate(System.currentTimeMillis());

        // When
        when(goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo)).thenReturn(activeGoal);
        Goal result = goalService.getActiveGoal(userInfo);

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
        Goal result = goalService.getActiveGoal(userInfo);

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

        Goal newGoal = new Goal(userInfo, attempts, successesAccuracy);
        newGoal.setId(1);

        // When
        when(goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo)).thenReturn(null);
        when(goalRepository.save(any(Goal.class))).thenReturn(newGoal);

        Goal result = goalService.createGoal(userInfo, attempts, successesAccuracy);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(10, result.getAttempts());
        assertEquals(0.8, result.getSuccessesAccuracy());
        verify(goalRepository, times(1)).findFirstByUserInfoOrderByBeginDateDesc(userInfo);
        verify(goalRepository, times(1)).save(any(Goal.class));
    }

    @Test
    void testCreateGoalWithExistingGoal() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        Integer attempts = 15;
        Double successesAccuracy = 0.9;

        Goal existingGoal = new Goal();
        existingGoal.setId(1);
        existingGoal.setUserInfo(userInfo);
        existingGoal.setAttempts(10);
        existingGoal.setSuccessesAccuracy(0.8);
        existingGoal.setBeginDate(System.currentTimeMillis() - 86400000); // 1 day ago

        Goal newGoal = new Goal(userInfo, attempts, successesAccuracy);
        newGoal.setId(2);

        // When
        when(goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo)).thenReturn(existingGoal);
        when(goalRepository.save(any(Goal.class))).thenReturn(newGoal);

        Goal result = goalService.createGoal(userInfo, attempts, successesAccuracy);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(15, result.getAttempts());
        assertEquals(0.9, result.getSuccessesAccuracy());
        verify(goalRepository, times(1)).findFirstByUserInfoOrderByBeginDateDesc(userInfo);
        verify(goalRepository, times(2)).save(any(Goal.class)); // Una vez para actualizar el existing y otra para el nuevo
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
        when(goalRepository.save(any(Goal.class))).thenReturn(null);

        // Then
        assertThrows(GoalNotCreatedException.class, () -> {
            goalService.createGoal(userInfo, attempts, successesAccuracy);
        });

        verify(goalRepository, times(1)).findFirstByUserInfoOrderByBeginDateDesc(userInfo);
        verify(goalRepository, times(1)).save(any(Goal.class));
    }
}
