package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.WordSenseNotFoundException;
import com.antonio.apprendrebackend.service.model.WordSense;
import com.antonio.apprendrebackend.service.repository.WordSenseRepository;
import com.antonio.apprendrebackend.service.service.impl.WordSenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordSenseServiceImplTest {
    @Mock
    private WordSenseRepository wordSenseRepository;

    @InjectMocks
    private WordSenseServiceImpl wordSenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByIdSuccess() {
        //Given
        int id = 1;
        WordSense wordSense = new WordSense();
        wordSense.setId(id);

        //When
        when(wordSenseRepository.findById(id)).thenReturn(Optional.of(wordSense));
        WordSense result = wordSenseService.getById(id);

        //Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(wordSenseRepository, times(1)).findById(id);
    }

    @Test
    void testGetByIdNotFound() {
        //Given
        int id = 999;

        //When
        when(wordSenseRepository.findById(id)).thenReturn(Optional.empty());

        //Then
        WordSenseNotFoundException exception = assertThrows(WordSenseNotFoundException.class, () -> {
            wordSenseService.getById(id);
        });

        assertEquals("Not found WordSense with id 999", exception.getMessage());
        verify(wordSenseRepository, times(1)).findById(id);
    }
}
