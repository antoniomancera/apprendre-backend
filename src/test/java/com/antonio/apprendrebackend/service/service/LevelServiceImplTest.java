package com.antonio.apprendrebackend.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antonio.apprendrebackend.service.model.Level;
import com.antonio.apprendrebackend.service.repository.LevelRepository;
import com.antonio.apprendrebackend.service.service.impl.LevelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class LevelServiceImplTest {

    @Mock
    private LevelRepository levelRepository;

    @InjectMocks
    private LevelServiceImpl levelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLevelsSuccess() {
        // Given
        Level level1 = new Level();
        level1.setId(1);
        level1.setCode(Level.LevelEnum.A1);

        Level level2 = new Level();
        level2.setId(2);
        level2.setCode(Level.LevelEnum.A2);

        Level level3 = new Level();
        level3.setId(3);
        level3.setCode(Level.LevelEnum.C1);

        List<Level> levels = Arrays.asList(level1, level2, level3);

        // When
        when(levelRepository.findAll()).thenReturn(levels);
        List<Level> result = levelService.getAllLevels();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(Level.LevelEnum.A1, result.get(0).getCode());
        assertEquals(Level.LevelEnum.A2, result.get(1).getCode());
        assertEquals(Level.LevelEnum.C1, result.get(2).getCode());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        verify(levelRepository, times(1)).findAll();
    }

    @Test
    void testGetAllLevelsEmpty() {
        // Given
        List<Level> emptyLevels = Collections.emptyList();

        // When
        when(levelRepository.findAll()).thenReturn(emptyLevels);
        List<Level> result = levelService.getAllLevels();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(levelRepository, times(1)).findAll();
    }

    @Test
    void testGetAllLevelsNull() {
        // Given
        // When
        when(levelRepository.findAll()).thenReturn(null);
        List<Level> result = levelService.getAllLevels();

        // Then
        assertNull(result);
        verify(levelRepository, times(1)).findAll();
    }

    @Test
    void testGetAllLevelsSingleLevel() {
        // Given
        Level singleLevel = new Level();
        singleLevel.setId(1);
        singleLevel.setCode(Level.LevelEnum.B1);

        List<Level> levels = Arrays.asList(singleLevel);

        // When
        when(levelRepository.findAll()).thenReturn(levels);
        List<Level> result = levelService.getAllLevels();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Level.LevelEnum.B1, result.get(0).getCode());
        assertEquals(1, result.get(0).getId());
        verify(levelRepository, times(1)).findAll();
    }

    @Test
    void testGetAllLevelsRepositoryCalledOnce() {
        // Given
        List<Level> levels = Arrays.asList(new Level(), new Level());

        // When
        when(levelRepository.findAll()).thenReturn(levels);
        levelService.getAllLevels();

        // Then
        verify(levelRepository, times(1)).findAll();
        verifyNoMoreInteractions(levelRepository);
    }
}