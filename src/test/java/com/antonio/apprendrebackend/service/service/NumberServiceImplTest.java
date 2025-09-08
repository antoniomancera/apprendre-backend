package com.antonio.apprendrebackend.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antonio.apprendrebackend.service.model.Number;
import com.antonio.apprendrebackend.service.repository.NumberRepository;
import com.antonio.apprendrebackend.service.service.impl.NumberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class NumberServiceImplTest {

    @Mock
    private NumberRepository numberRepository;

    @InjectMocks
    private NumberServiceImpl numberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNumbersSuccess() {
        // Given
        Number number1 = new Number();
        number1.setId(1);
        number1.setCode("Singular");

        Number number2 = new Number();
        number2.setId(2);
        number2.setCode("Plural");

        List<Number> numbers = Arrays.asList(number1, number2);

        // When
        when(numberRepository.findAll()).thenReturn(numbers);
        List<Number> result = numberService.getAllNumbers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Singular", result.get(0).getCode());
        assertEquals("Plural", result.get(1).getCode());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        verify(numberRepository, times(1)).findAll();
    }

    @Test
    void testGetAllNumbersEmpty() {
        // Given
        List<Number> emptyNumbers = Collections.emptyList();

        // When
        when(numberRepository.findAll()).thenReturn(emptyNumbers);
        List<Number> result = numberService.getAllNumbers();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(numberRepository, times(1)).findAll();
    }

    @Test
    void testGetAllNumbersNull() {
        // Given / When
        when(numberRepository.findAll()).thenReturn(null);
        List<Number> result = numberService.getAllNumbers();

        // Then
        assertNull(result);
        verify(numberRepository, times(1)).findAll();
    }

    @Test
    void testGetAllNumbersSingleNumber() {
        // Given
        Number singleNumber = new Number();
        singleNumber.setId(1);
        singleNumber.setCode("Dual");

        List<Number> numbers = Arrays.asList(singleNumber);

        // When
        when(numberRepository.findAll()).thenReturn(numbers);
        List<Number> result = numberService.getAllNumbers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Dual", result.get(0).getCode());
        assertEquals(1, result.get(0).getId());
        verify(numberRepository, times(1)).findAll();
    }

    @Test
    void testGetAllNumbersRepositoryCalledOnce() {
        // Given
        List<Number> numbers = Arrays.asList(new Number(), new Number());

        // When
        when(numberRepository.findAll()).thenReturn(numbers);
        numberService.getAllNumbers();

        // Then
        verify(numberRepository, times(1)).findAll();
        verifyNoMoreInteractions(numberRepository);
    }
}