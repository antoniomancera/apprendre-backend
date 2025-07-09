package com.antonio.apprendrebackend.service.service;


import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordNotFoundException;
import com.antonio.apprendrebackend.service.mapper.WordMapper;
import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.model.Word;
import com.antonio.apprendrebackend.service.repository.WordRepository;
import com.antonio.apprendrebackend.service.service.impl.WordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordServiceImplTest {
    @Mock
    private WordRepository wordRepository;

    @Mock
    private TypeService typeService;

    @Mock
    private WordMapper wordMapper;

    @InjectMocks
    private WordServiceImpl wordService;

    private Type verbType;
    private Word verb1;
    private Word verb2;
    private WordDTO verbDTO1;
    private WordDTO verbDTO2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Common test data
        verbType = new Type();
        verbType.setId(1);
        verbType.setType(Type.TypeEnum.VERB);

        verb1 = new Word();
        verb1.setId(1);
        verb1.setName("run");
        verb1.setType(verbType);

        verb2 = new Word();
        verb2.setId(2);
        verb2.setName("jump");
        verb2.setType(verbType);

        verbDTO1 = new WordDTO();
        verbDTO1.setName("run");

        verbDTO2 = new WordDTO();
        verbDTO2.setName("jump");
    }

    @Test
    void testGetAllVerbsSuccess() {
        // Given
        List<Word> verbList = Arrays.asList(verb1, verb2);

        // When
        when(typeService.getByType(Type.TypeEnum.VERB)).thenReturn(verbType);
        when(wordRepository.findByType(verbType)).thenReturn(verbList);
        when(wordMapper.toDTO(verb1)).thenReturn(verbDTO1);
        when(wordMapper.toDTO(verb2)).thenReturn(verbDTO2);

        List<WordDTO> result = wordService.getAllVerbs();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("run", result.get(0).getName());
        assertEquals("jump", result.get(1).getName());
        verify(typeService, times(1)).getByType(Type.TypeEnum.VERB);
        verify(wordRepository, times(1)).findByType(verbType);
        verify(wordMapper, times(2)).toDTO(any(Word.class));
    }

    @Test
    void testGetAllVerbsEmpty() {
        // Given
        List<Word> emptyVerbList = new ArrayList<>();

        // When
        when(typeService.getByType(Type.TypeEnum.VERB)).thenReturn(verbType);
        when(wordRepository.findByType(verbType)).thenReturn(emptyVerbList);

        // Then
        WordNotFoundException exception = assertThrows(WordNotFoundException.class, () -> {
            wordService.getAllVerbs();
        });

        assertEquals("Not found any verb", exception.getMessage());
        verify(typeService, times(1)).getByType(Type.TypeEnum.VERB);
        verify(wordRepository, times(1)).findByType(verbType);
        verify(wordMapper, never()).toDTO(any(Word.class));
    }

    @Test
    void testGetAllVerbsTypeNotFound() {
        // Given
        TypeNotFoundException typeNotFoundException = new TypeNotFoundException("Not found any type VERB");

        // When
        when(typeService.getByType(Type.TypeEnum.VERB)).thenThrow(typeNotFoundException);

        // Then
        TypeNotFoundException exception = assertThrows(TypeNotFoundException.class, () -> {
            wordService.getAllVerbs();
        });

        assertEquals("Not found any type VERB", exception.getMessage());
        verify(typeService, times(1)).getByType(Type.TypeEnum.VERB);
        verify(wordRepository, never()).findByType(any(Type.class));
        verify(wordMapper, never()).toDTO(any(Word.class));
    }

    @Test
    void testGetAllVerbsSingleVerb() {
        // Given
        List<Word> singleVerbList = Arrays.asList(verb1);

        // When
        when(typeService.getByType(Type.TypeEnum.VERB)).thenReturn(verbType);
        when(wordRepository.findByType(verbType)).thenReturn(singleVerbList);
        when(wordMapper.toDTO(verb1)).thenReturn(verbDTO1);

        List<WordDTO> result = wordService.getAllVerbs();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("run", result.get(0).getName());
        verify(typeService, times(1)).getByType(Type.TypeEnum.VERB);
        verify(wordRepository, times(1)).findByType(verbType);
        verify(wordMapper, times(1)).toDTO(verb1);
    }

    @Test
    void testGetAllVerbsMapperReturnsNull() {
        // Given
        List<Word> verbList = Arrays.asList(verb1);

        // When
        when(typeService.getByType(Type.TypeEnum.VERB)).thenReturn(verbType);
        when(wordRepository.findByType(verbType)).thenReturn(verbList);
        when(wordMapper.toDTO(verb1)).thenReturn(null);

        List<WordDTO> result = wordService.getAllVerbs();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0));
        verify(typeService, times(1)).getByType(Type.TypeEnum.VERB);
        verify(wordRepository, times(1)).findByType(verbType);
        verify(wordMapper, times(1)).toDTO(verb1);
    }
}
