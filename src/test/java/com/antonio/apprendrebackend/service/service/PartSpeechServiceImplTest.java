package com.antonio.apprendrebackend.service.service;


import com.antonio.apprendrebackend.service.exception.PartSpeechFoundException;
import com.antonio.apprendrebackend.service.model.PartSpeech;
import com.antonio.apprendrebackend.service.repository.PartSpeechRepository;
import com.antonio.apprendrebackend.service.service.impl.PartSpeechServiceImpl;
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

public class PartSpeechServiceImplTest {
    @Mock
    private PartSpeechRepository partSpeechRepository;

    @InjectMocks
    private PartSpeechServiceImpl partSpeechService;

    private PartSpeech partSpeech;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        partSpeech = new PartSpeech();
        partSpeech.setId(1);
        partSpeech.setCode(PartSpeech.PartSpeechEnum.VERB);
    }

    @Test
    void testGetByPartSpeechSuccess() {
        // Given
        PartSpeech.PartSpeechEnum partSpeechEnum = PartSpeech.PartSpeechEnum.VERB;
        Optional<PartSpeech> PartSpeechOptional = Optional.of(partSpeech);

        // When
        when(partSpeechRepository.findByCode(partSpeechEnum)).thenReturn(PartSpeechOptional);
        PartSpeech result = partSpeechService.getByPartSpeech(partSpeechEnum);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(PartSpeech.PartSpeechEnum.VERB, result.getCode());
        verify(partSpeechRepository, times(1)).findByCode(partSpeechEnum);
    }

    @Test
    void testGetByPartSpeechNotFound() {
        // Given
        PartSpeech.PartSpeechEnum partSpeechEnum = PartSpeech.PartSpeechEnum.SUSTANTIVE;
        Optional<PartSpeech> emptyOptional = Optional.empty();

        // When
        when(partSpeechRepository.findByCode(partSpeechEnum)).thenReturn(emptyOptional);

        // Then
        PartSpeechFoundException exception = assertThrows(PartSpeechFoundException.class, () -> {
            partSpeechService.getByPartSpeech(partSpeechEnum);
        });

        assertTrue(exception.getMessage().contains("Not found any PartSpeech"));
        assertTrue(exception.getMessage().contains(partSpeechEnum.toString()));
        verify(partSpeechRepository, times(1)).findByCode(partSpeechEnum);
    }

    @Test
    void testGetByPartSpeechWithDifferentEnumValues() {
        // Given
        PartSpeech.PartSpeechEnum partSpeechEnum = PartSpeech.PartSpeechEnum.SUSTANTIVE;
        PartSpeech phrasePartSpeech = new PartSpeech();
        phrasePartSpeech.setId(2);
        phrasePartSpeech.setCode(PartSpeech.PartSpeechEnum.SUSTANTIVE);
        Optional<PartSpeech> partSpeechOptional = Optional.of(phrasePartSpeech);

        // When
        when(partSpeechRepository.findByCode(partSpeechEnum)).thenReturn(partSpeechOptional);

        PartSpeech result = partSpeechService.getByPartSpeech(partSpeechEnum);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(PartSpeech.PartSpeechEnum.SUSTANTIVE, result.getCode());
        verify(partSpeechRepository, times(1)).findByCode(partSpeechEnum);
    }

    @Test
    void testGetAllPartSpeechSuccess() {
        // Given
        PartSpeech partSpeech1 = new PartSpeech();
        partSpeech1.setId(1);
        partSpeech1.setCode(PartSpeech.PartSpeechEnum.SUSTANTIVE);

        PartSpeech partSpeech2 = new PartSpeech();
        partSpeech2.setId(2);
        partSpeech2.setCode(PartSpeech.PartSpeechEnum.VERB);

        PartSpeech partSpeech3 = new PartSpeech();
        partSpeech3.setId(3);
        partSpeech3.setCode(PartSpeech.PartSpeechEnum.ADJECTIVE);

        List<PartSpeech> partSpeeches = Arrays.asList(partSpeech1, partSpeech2, partSpeech3);

        // When
        when(partSpeechRepository.findAll()).thenReturn(partSpeeches);
        List<PartSpeech> result = partSpeechService.getAllPartSpeechs();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        assertEquals(PartSpeech.PartSpeechEnum.SUSTANTIVE, result.get(0).getCode());
        assertEquals(PartSpeech.PartSpeechEnum.VERB, result.get(1).getCode());
        assertEquals(PartSpeech.PartSpeechEnum.ADJECTIVE, result.get(2).getCode());
        verify(partSpeechRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPartSpeechsEmpty() {
        // Given
        List<PartSpeech> emptyPartSpeeches = Collections.emptyList();

        // When
        when(partSpeechRepository.findAll()).thenReturn(emptyPartSpeeches);
        List<PartSpeech> result = partSpeechService.getAllPartSpeechs();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(partSpeechRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPartSpeechsNull() {
        // Given / When
        when(partSpeechRepository.findAll()).thenReturn(null);
        List<PartSpeech> result = partSpeechService.getAllPartSpeechs();

        // Then
        assertNull(result);
        verify(partSpeechRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPartSpeechsSinglePartSpeech() {
        // Given
        PartSpeech singlePartSpeech = new PartSpeech();
        singlePartSpeech.setId(1);
        singlePartSpeech.setCode(PartSpeech.PartSpeechEnum.ADVERB);

        List<PartSpeech> partSpeeches = Arrays.asList(singlePartSpeech);

        // When
        when(partSpeechRepository.findAll()).thenReturn(partSpeeches);
        List<PartSpeech> result = partSpeechService.getAllPartSpeechs();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(PartSpeech.PartSpeechEnum.ADVERB, result.get(0).getCode());
        verify(partSpeechRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTypesRepositoryCalledOnce() {
        // Given
        List<PartSpeech> partSpeeches = Arrays.asList(new PartSpeech(), new PartSpeech());

        // When
        when(partSpeechRepository.findAll()).thenReturn(partSpeeches);
        partSpeechService.getAllPartSpeechs();

        // Then
        verify(partSpeechRepository, times(1)).findAll();
        verifyNoMoreInteractions(partSpeechRepository);
    }
}
