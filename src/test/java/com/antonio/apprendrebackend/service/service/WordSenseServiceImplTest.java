package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.WordSenseNotFoundException;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.WordSenseRepository;
import com.antonio.apprendrebackend.service.service.impl.WordSenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Test
    void testGetByWordIdSuccess() {
        //Given
        int id = 1;
        Word word = new Word();
        word.setId(id);
        WordSense wordSense = new WordSense();
        wordSense.setWord(word);
        List<WordSense> wordSenses = new ArrayList<>();
        wordSenses.add(wordSense);

        //When
        when(wordSenseRepository.findByWordId(id)).thenReturn(wordSenses);
        List<WordSense> result = wordSenseService.getWordSensesByWordId(id);

        //Then
        assertNotNull(result);
        assertEquals(id, result.stream().findFirst().get().getWord().getId());
        verify(wordSenseRepository, times(1)).findByWordId(id);
    }

    @Test
    void testGetByWordIdNotFound() {
        //Given
        int id = 999;
        Word word = new Word();
        word.setId(id);
        WordSense wordSense = new WordSense();
        wordSense.setWord(word);
        List<WordSense> wordSenses = new ArrayList<>();
        wordSenses.add(wordSense);


        //When
        when(wordSenseRepository.findById(id)).thenReturn(Optional.empty());

        //Then
        WordSenseNotFoundException exception = assertThrows(WordSenseNotFoundException.class, () -> {
            wordSenseService.getById(id);
        });

        assertEquals("Not found WordSense with id 999", exception.getMessage());
        verify(wordSenseRepository, times(1)).findById(id);
    }

    @Test
    void testGetWordSensesByWordIdSuccess() {
        // Given
        int wordId = 1;
        Word word = new Word();
        word.setId(wordId);
        WordSense wordSense = new WordSense();
        wordSense.setWord(word);
        List<WordSense> wordSenses = Arrays.asList(wordSense);

        // When
        when(wordSenseRepository.findByWordId(wordId)).thenReturn(wordSenses);
        List<WordSense> result = wordSenseService.getWordSensesByWordId(wordId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(wordId, result.get(0).getWord().getId());
        verify(wordSenseRepository, times(1)).findByWordId(wordId);
    }

    @Test
    void testGetWordSensesByWordIdEmpty() {
        // Given
        int wordId = 999;
        List<WordSense> emptyWordSenses = new ArrayList<>();

        // When
        when(wordSenseRepository.findByWordId(wordId)).thenReturn(emptyWordSenses);
        List<WordSense> result = wordSenseService.getWordSensesByWordId(wordId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordSenseRepository, times(1)).findByWordId(wordId);
    }

    @Test
    void testGetWordSensesByWordIdWithSpecificationSuccess() {
        // Given
        int wordId = 1;
        Word word = new Word();
        word.setId(wordId);

        WordSense wordSense1 = new WordSense();
        wordSense1.setId(1);
        wordSense1.setWord(word);

        WordSense wordSense2 = new WordSense();
        wordSense2.setId(2);
        wordSense2.setWord(word);

        List<WordSense> wordSenses = Arrays.asList(wordSense1, wordSense2);

        Specification<WordSense> mockSpec = mock(Specification.class);
        Specification<WordSense> combinedSpec = mock(Specification.class);

        // When
        when(mockSpec.and(any(Specification.class))).thenReturn(combinedSpec);
        when(wordSenseRepository.findAll(combinedSpec)).thenReturn(wordSenses);

        List<WordSense> result = wordSenseService.getWordSensesByWordIdWithSpecification(mockSpec, wordId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(wordId, result.get(0).getWord().getId());
        assertEquals(wordId, result.get(1).getWord().getId());
        verify(mockSpec, times(1)).and(any(Specification.class));
        verify(wordSenseRepository, times(1)).findAll(combinedSpec);
    }

    @Test
    void testGetWordSensesByWordIdWithSpecificationEmpty() {
        // Given
        int wordId = 999;
        List<WordSense> emptyWordSenses = new ArrayList<>();

        Specification<WordSense> mockSpec = mock(Specification.class);
        Specification<WordSense> combinedSpec = mock(Specification.class);

        // When
        when(mockSpec.and(any(Specification.class))).thenReturn(combinedSpec);
        when(wordSenseRepository.findAll(combinedSpec)).thenReturn(emptyWordSenses);

        List<WordSense> result = wordSenseService.getWordSensesByWordIdWithSpecification(mockSpec, wordId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mockSpec, times(1)).and(any(Specification.class));
        verify(wordSenseRepository, times(1)).findAll(combinedSpec);
    }

    @Test
    void testGetWordSensesByWordIdAndDeckIdSuccess() {
        // Given
        int wordId = 1;
        int deckId = 10;

        Word word = new Word();
        word.setId(wordId);

        Deck deck = new Deck();
        deck.setId(deckId);

        WordSense wordSense1 = new WordSense();
        wordSense1.setId(1);
        wordSense1.setWord(word);

        WordSense wordSense2 = new WordSense();
        wordSense2.setId(2);
        wordSense2.setWord(word);

        List<WordSense> wordSenses = Arrays.asList(wordSense1, wordSense2);

        // When
        when(wordSenseRepository.findByWordIdAndDeckId(wordId, deckId)).thenReturn(wordSenses);
        List<WordSense> result = wordSenseService.getWordSensesByWordIdAndDeckId(wordId, deckId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(wordId, result.get(0).getWord().getId());
        assertEquals(wordId, result.get(1).getWord().getId());
        verify(wordSenseRepository, times(1)).findByWordIdAndDeckId(wordId, deckId);
    }

    @Test
    void testGetWordSensesByWordIdAndDeckIdEmpty() {
        // Given
        int wordId = 999;
        int deckId = 888;
        List<WordSense> emptyWordSenses = new ArrayList<>();

        // When
        when(wordSenseRepository.findByWordIdAndDeckId(wordId, deckId)).thenReturn(emptyWordSenses);
        List<WordSense> result = wordSenseService.getWordSensesByWordIdAndDeckId(wordId, deckId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordSenseRepository, times(1)).findByWordIdAndDeckId(wordId, deckId);
    }

    @Test
    void testGetWordSensesByDeckIdSuccess() {
        // Given
        int deckId = 10;

        Deck deck = new Deck();
        deck.setId(deckId);

        WordSense wordSense1 = new WordSense();
        wordSense1.setId(1);

        WordSense wordSense2 = new WordSense();
        wordSense2.setId(2);

        WordSense wordSense3 = new WordSense();
        wordSense3.setId(3);

        List<WordSense> wordSenses = Arrays.asList(wordSense1, wordSense2, wordSense3);

        // When
        when(wordSenseRepository.findTargetLanguageWordSensesByDeckId(deckId)).thenReturn(wordSenses);
        List<WordSense> result = wordSenseService.getTargetLanguageWordSensesByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(wordSense1.getId(), result.get(0).getId());
        assertEquals(wordSense2.getId(), result.get(1).getId());
        assertEquals(wordSense3.getId(), result.get(2).getId());
        verify(wordSenseRepository, times(1)).findTargetLanguageWordSensesByDeckId(deckId);
    }

    @Test
    void testGetWordSensesByDeckIdEmpty() {
        // Given
        int deckId = 999;
        List<WordSense> emptyWordSenses = new ArrayList<>();

        // When
        when(wordSenseRepository.findTargetLanguageWordSensesByDeckId(deckId)).thenReturn(emptyWordSenses);
        List<WordSense> result = wordSenseService.getTargetLanguageWordSensesByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordSenseRepository, times(1)).findTargetLanguageWordSensesByDeckId(deckId);
    }

    @Test
    void testGetWordSensesByDeckIdWithNullParameter() {
        // Given
        Integer deckId = null;

        // When
        when(wordSenseRepository.findTargetLanguageWordSensesByDeckId(deckId)).thenReturn(new ArrayList<>());
        List<WordSense> result = wordSenseService.getTargetLanguageWordSensesByDeckId(deckId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordSenseRepository, times(1)).findTargetLanguageWordSensesByDeckId(deckId);
    }

    @Test
    void testGetWordSensesByWordIdAndDeckIdWithNullParameters() {
        // Given
        Integer wordId = null;
        Integer deckId = null;

        // When
        when(wordSenseRepository.findByWordIdAndDeckId(wordId, deckId)).thenReturn(new ArrayList<>());
        List<WordSense> result = wordSenseService.getWordSensesByWordIdAndDeckId(wordId, deckId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordSenseRepository, times(1)).findByWordIdAndDeckId(wordId, deckId);
    }
}
