package com.antonio.apprendrebackend.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antonio.apprendrebackend.service.model.Mood;
import com.antonio.apprendrebackend.service.repository.MoodRepository;
import com.antonio.apprendrebackend.service.service.impl.MoodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class MoodServiceImplTest {

    @Mock
    private MoodRepository moodRepository;

    @InjectMocks
    private MoodServiceImpl moodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMoodsSuccess() {
        // Given
        Mood mood1 = new Mood();
        mood1.setId(1);
        mood1.setCode("Indicative");

        Mood mood2 = new Mood();
        mood2.setId(2);
        mood2.setCode("Subjunctive");

        Mood mood3 = new Mood();
        mood3.setId(3);
        mood3.setCode("Imperative");

        List<Mood> moods = Arrays.asList(mood1, mood2, mood3);

        // When
        when(moodRepository.findAll()).thenReturn(moods);
        List<Mood> result = moodService.getAllMoods();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Indicative", result.get(0).getCode());
        assertEquals("Subjunctive", result.get(1).getCode());
        assertEquals("Imperative", result.get(2).getCode());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        verify(moodRepository, times(1)).findAll();
    }

    @Test
    void testGetAllMoodsEmpty() {
        // Given
        List<Mood> emptyMoods = Collections.emptyList();

        // When
        when(moodRepository.findAll()).thenReturn(emptyMoods);
        List<Mood> result = moodService.getAllMoods();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(moodRepository, times(1)).findAll();
    }

    @Test
    void testGetAllMoodsNull() {
        // Given / When
        when(moodRepository.findAll()).thenReturn(null);
        List<Mood> result = moodService.getAllMoods();

        // Then
        assertNull(result);
        verify(moodRepository, times(1)).findAll();
    }

    @Test
    void testGetAllMoodsSingleMood() {
        // Given
        Mood singleMood = new Mood();
        singleMood.setId(1);
        singleMood.setCode("Conditional");

        List<Mood> moods = Arrays.asList(singleMood);

        // When
        when(moodRepository.findAll()).thenReturn(moods);
        List<Mood> result = moodService.getAllMoods();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Conditional", result.get(0).getCode());
        assertEquals(1, result.get(0).getId());
        verify(moodRepository, times(1)).findAll();
    }

    @Test
    void testGetAllMoodsRepositoryCalledOnce() {
        // Given
        List<Mood> moods = Arrays.asList(new Mood(), new Mood());

        // When
        when(moodRepository.findAll()).thenReturn(moods);
        moodService.getAllMoods();

        // Then
        verify(moodRepository, times(1)).findAll();
        verifyNoMoreInteractions(moodRepository);
    }
}