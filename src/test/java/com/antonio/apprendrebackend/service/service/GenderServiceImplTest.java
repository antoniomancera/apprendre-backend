package com.antonio.apprendrebackend.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antonio.apprendrebackend.service.model.Gender;
import com.antonio.apprendrebackend.service.repository.GenderRepository;
import com.antonio.apprendrebackend.service.service.impl.GenderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class GenderServiceImplTest {
    @Mock
    private GenderRepository genderRepository;

    @InjectMocks
    private GenderServiceImpl genderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllGendersSuccess() {
        // Given
        Gender gender1 = new Gender();
        gender1.setId(1);
        gender1.setCode("Masculine");

        Gender gender2 = new Gender();
        gender2.setId(2);
        gender2.setCode("Feminine");

        List<Gender> genders = Arrays.asList(gender1, gender2);

        // When
        when(genderRepository.findAll()).thenReturn(genders);
        List<Gender> result = genderService.getAllGenders();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Masculine", result.get(0).getCode());
        assertEquals("Feminine", result.get(1).getCode());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        verify(genderRepository, times(1)).findAll();
    }

    @Test
    void testGetAllGendersEmpty() {
        // Given
        List<Gender> emptyGenders = Collections.emptyList();

        // When
        when(genderRepository.findAll()).thenReturn(emptyGenders);
        List<Gender> result = genderService.getAllGenders();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(genderRepository, times(1)).findAll();
    }

    @Test
    void testGetAllGendersNull() {
        // Given
        // When
        when(genderRepository.findAll()).thenReturn(null);
        List<Gender> result = genderService.getAllGenders();

        // Then
        assertNull(result);
        verify(genderRepository, times(1)).findAll();
    }

    @Test
    void testGetAllGendersSingleGender() {
        // Given
        Gender singleGender = new Gender();
        singleGender.setId(1);
        singleGender.setCode("Neuter");

        List<Gender> genders = Arrays.asList(singleGender);

        // When
        when(genderRepository.findAll()).thenReturn(genders);
        List<Gender> result = genderService.getAllGenders();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Neuter", result.get(0).getCode());
        assertEquals(1, result.get(0).getId());
        verify(genderRepository, times(1)).findAll();
    }

    @Test
    void testGetAllGendersRepositoryCalledOnce() {
        // Given
        List<Gender> genders = Arrays.asList(new Gender(), new Gender());

        // When
        when(genderRepository.findAll()).thenReturn(genders);
        genderService.getAllGenders();

        // Then
        verify(genderRepository, times(1)).findAll();
        verifyNoMoreInteractions(genderRepository);
    }
}