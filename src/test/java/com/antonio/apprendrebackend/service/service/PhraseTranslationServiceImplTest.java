package com.antonio.apprendrebackend.service.service;


import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.mapper.PhraseTranslationMapper;
import com.antonio.apprendrebackend.service.mapper.WordTranslationMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.PhraseTranslationRepository;
import com.antonio.apprendrebackend.service.service.impl.PhraseTranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PhraseTranslationServiceImplTest {
    @InjectMocks
    private PhraseTranslationServiceImpl phraseTranslationService;

    @Mock
    private PhraseTranslationRepository phraseTranslationRepository;

    @Mock
    private WordPhraseTranslationService wordPhraseTranslationService;

    @Mock
    private DeckService deckService;

    @Mock
    private DeckWordPhraseTranslationService deckWordPhraseTranslationService;

    @Mock
    private WordTranslationMapper wordTranslationMapper;

    @Mock
    private PhraseTranslationMapper phraseTranslationMapper;
    private Integer deckId;
    private Phrase phraseFr1;
    private Phrase phraseFr2;
    private Phrase phraseSp1;
    private Phrase phraseSp2;

    private Language languageFr;
    private Language languageSp;
    private Deck deck;
    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        languageFr = new Language();
        languageFr.setCode(Language.LanguageEnum.FR);

        languageSp = new Language();
        languageSp.setCode(Language.LanguageEnum.ES);

        deckId = 1;

        phraseFr1 = new Phrase();
        phraseFr1.setPhrase("Bonjour tout le monde");
        phraseFr1.setLanguage(languageFr);

        phraseFr2 = new Phrase();
        phraseFr2.setPhrase("Comment ça va?");
        phraseFr2.setLanguage(languageFr);

        phraseSp1 = new Phrase();
        phraseSp1.setPhrase("Hola a todos");
        phraseSp1.setLanguage(languageSp);

        phraseSp2 = new Phrase();
        phraseSp2.setPhrase("¿Cómo estás?");
        phraseSp2.setLanguage(languageSp);

        course = new Course();
        course.setTargetLanguage(languageFr);
        course.setBaseLanguage(languageSp);

        deck = new Deck();
        deck.setId(deckId);
        deck.setName("general");
        deck.setCourse(course);
    }

    @Test
    void testGetAllPhrasesWithWordTranslationsByDeck_ReturnsPhrases() {
        // Given


        PhraseTranslation phraseTranslation1 = new PhraseTranslation();
        phraseTranslation1.setId(1);
        phraseTranslation1.setPhraseA(phraseFr1);
        phraseTranslation1.setPhraseB(phraseSp1);

        PhraseTranslation phraseTranslation2 = new PhraseTranslation();
        phraseTranslation2.setId(2);
        phraseTranslation2.setPhraseA(phraseFr2);
        phraseTranslation2.setPhraseB(phraseSp2);

        List<PhraseTranslation> phrases = Arrays.asList(phraseTranslation1, phraseTranslation2);

        WordTranslation wordTranslation1 = new WordTranslation();
        wordTranslation1.setId(1);

        WordTranslation wordTranslation2 = new WordTranslation();
        wordTranslation2.setId(2);

        List<WordTranslation> wordTranslations1 = Arrays.asList(wordTranslation1);
        List<WordTranslation> wordTranslations2 = Arrays.asList(wordTranslation2);

        PhraseTranslationDTO phraseDTO1 = new PhraseTranslationDTO();
        phraseDTO1.setId(1);

        PhraseTranslationDTO phraseDTO2 = new PhraseTranslationDTO();
        phraseDTO2.setId(2);

        WordTranslationDTO wordTranslationDTO1 = new WordTranslationDTO();
        wordTranslationDTO1.setId(1);

        WordTranslationDTO wordTranslationDTO2 = new WordTranslationDTO();
        wordTranslationDTO2.setId(2);

        when(deckWordPhraseTranslationService.getPhraseTranslationsByDeckId(deckId)).thenReturn(phrases);
        when(wordPhraseTranslationService.getWordTranslationsByDeckIdPhraseTranslationId(deckId, 1)).thenReturn(wordTranslations1);
        when(wordPhraseTranslationService.getWordTranslationsByDeckIdPhraseTranslationId(deckId, 2)).thenReturn(wordTranslations2);
        when(phraseTranslationMapper.toDTO(phraseTranslation1, languageFr)).thenReturn(phraseDTO1);
        when(phraseTranslationMapper.toDTO(phraseTranslation2, languageFr)).thenReturn(phraseDTO2);
        when(wordTranslationMapper.toDTO(wordTranslation1, languageFr)).thenReturn(wordTranslationDTO1);
        when(wordTranslationMapper.toDTO(wordTranslation2, languageFr)).thenReturn(wordTranslationDTO2);
        when(deckService.getDeckbyId(deckId)).thenReturn(deck);

        // When
        List<PhraseTranslationWithWordTranslationsDTO> result = phraseTranslationService.getAllPhrasesWithWordTranslationsByDeck(deckId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(phraseDTO1, result.get(0).getPhrase());
        assertEquals(1, result.get(0).getWordTranslations().size());
        assertEquals(wordTranslationDTO1, result.get(0).getWordTranslations().get(0));
        assertEquals(phraseDTO2, result.get(1).getPhrase());
        assertEquals(1, result.get(1).getWordTranslations().size());
        assertEquals(wordTranslationDTO2, result.get(1).getWordTranslations().get(0));

        verify(deckWordPhraseTranslationService, times(1)).getPhraseTranslationsByDeckId(deckId);
        verify(wordPhraseTranslationService, times(1)).getWordTranslationsByDeckIdPhraseTranslationId(deckId, 1);
        verify(wordPhraseTranslationService, times(1)).getWordTranslationsByDeckIdPhraseTranslationId(deckId, 2);
        verify(phraseTranslationMapper, times(1)).toDTO(phraseTranslation1, languageFr);
        verify(phraseTranslationMapper, times(1)).toDTO(phraseTranslation2, languageFr);
        verify(wordTranslationMapper, times(1)).toDTO(wordTranslation1, languageFr);
        verify(wordTranslationMapper, times(1)).toDTO(wordTranslation2, languageFr);
    }

    @Test
    void testGetAllPhrasesWithWordTranslationsByDeck_ThrowsExceptionWhenNoPhrasesFound() {
        // Given
        Integer deckId = 1;
        when(deckWordPhraseTranslationService.getPhraseTranslationsByDeckId(deckId)).thenReturn(Collections.emptyList());

        // When / Then
        PhraseNotFoundException exception = assertThrows(
                PhraseNotFoundException.class,
                () -> phraseTranslationService.getAllPhrasesWithWordTranslationsByDeck(deckId)
        );

        assertEquals(String.format("Not found any phrase of deck %s", deckId), exception.getMessage());
        verify(deckWordPhraseTranslationService, times(1)).getPhraseTranslationsByDeckId(deckId);
        verify(wordPhraseTranslationService, never()).getWordTranslationsByDeckIdPhraseTranslationId(anyInt(), anyInt());
    }

    @Test
    void testGetAllPhrases_ReturnsPhrases() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Integer deckId = 1;
        Phrase phraseFr1 = new Phrase();
        phraseFr1.setPhrase("Bonjour tout le monde");
        Phrase phraseFr2 = new Phrase();
        phraseFr2.setPhrase("Comment ça va?");
        Phrase phraseSp1 = new Phrase();
        phraseSp1.setPhrase("Hola a todos");
        Phrase phraseSp2 = new Phrase();
        phraseSp2.setPhrase("¿Cómo estás?");

        PhraseTranslation phraseTranslation1 = new PhraseTranslation();
        phraseTranslation1.setId(1);
        phraseTranslation1.setPhraseA(phraseFr1);
        phraseTranslation1.setPhraseB(phraseSp1);

        PhraseTranslation phraseTranslation2 = new PhraseTranslation();
        phraseTranslation2.setId(2);
        phraseTranslation2.setPhraseA(phraseFr2);
        phraseTranslation2.setPhraseB(phraseSp2);


        List<PhraseTranslation> phrases = Arrays.asList(phraseTranslation1, phraseTranslation2);
        Page<PhraseTranslation> phrasePage = new PageImpl<>(phrases, pageable, phrases.size());

        PhraseTranslationDTO phraseDTO1 = new PhraseTranslationDTO();
        phraseDTO1.setId(1);

        PhraseTranslationDTO phraseDTO2 = new PhraseTranslationDTO();
        phraseDTO2.setId(2);

        when(phraseTranslationRepository.findAll(pageable)).thenReturn(phrasePage);
        when(phraseTranslationMapper.toDTO(phraseTranslation1, languageFr)).thenReturn(phraseDTO1);
        when(phraseTranslationMapper.toDTO(phraseTranslation2, languageFr)).thenReturn(phraseDTO2);

        // When
        List<PhraseTranslationDTO> result = phraseTranslationService.getAllTargetLanguagePhrases(pageNumber, pageSize, languageFr);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(phraseDTO1, result.get(0));
        assertEquals(phraseDTO2, result.get(1));

        verify(phraseTranslationRepository, times(1)).findAll(pageable);
        verify(phraseTranslationMapper, times(1)).toDTO(phraseTranslation1, languageFr);
        verify(phraseTranslationMapper, times(1)).toDTO(phraseTranslation2, languageFr);
    }

    @Test
    void testGetAllPhrases_ThrowsExceptionWhenNoPhrasesFound() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<PhraseTranslation> phrases = Collections.emptyList();
        Page<PhraseTranslation> phrasePage = new PageImpl<>(phrases, pageable, phrases.size());

        when(phraseTranslationRepository.findAll(pageable)).thenReturn(phrasePage);

        // When / Then
        PhraseNotFoundException exception = assertThrows(
                PhraseNotFoundException.class,
                () -> phraseTranslationService.getAllTargetLanguagePhrases(pageNumber, pageSize, languageFr)
        );

        assertEquals("Not found any phrase", exception.getMessage());
        verify(phraseTranslationRepository, times(1)).findAll(pageable);
        verify(phraseTranslationMapper, never()).toDTO(any(), isNull());
    }

    @Test
    void testGetAllPhrasesWithWordTranslationsByDeck_WithEmptyWordTranslations() {
        // Given
        Integer deckId = 1;

        Phrase phraseFr = new Phrase();
        phraseFr.setPhrase("Bonjour tout le monde");

        Phrase phraseSp = new Phrase();
        phraseSp.setPhrase("Hola a todos");


        PhraseTranslation phraseTranslation = new PhraseTranslation();
        phraseTranslation.setId(1);
        phraseTranslation.setPhraseA(phraseFr);
        phraseTranslation.setPhraseB(phraseSp);


        List<PhraseTranslation> phrases = List.of(phraseTranslation);
        List<WordTranslation> emptyWordTranslations = Collections.emptyList();

        PhraseTranslationDTO phraseDTO = new PhraseTranslationDTO();
        phraseDTO.setId(1);

        when(deckWordPhraseTranslationService.getPhraseTranslationsByDeckId(deckId)).thenReturn(phrases);
        when(wordPhraseTranslationService.getWordTranslationsByDeckIdPhraseTranslationId(deckId, 1)).thenReturn(emptyWordTranslations);
        when(phraseTranslationMapper.toDTO(phraseTranslation, languageFr)).thenReturn(phraseDTO);
        when(deckService.getDeckbyId(deckId)).thenReturn(deck);

        // When
        List<PhraseTranslationWithWordTranslationsDTO> result = phraseTranslationService.getAllPhrasesWithWordTranslationsByDeck(deckId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(phraseDTO, result.get(0).getPhrase());
        assertTrue(result.get(0).getWordTranslations().isEmpty());

        verify(deckWordPhraseTranslationService, times(1)).getPhraseTranslationsByDeckId(deckId);
        verify(wordPhraseTranslationService, times(1)).getWordTranslationsByDeckIdPhraseTranslationId(deckId, 1);
        verify(phraseTranslationMapper, times(1)).toDTO(phraseTranslation, languageFr);
        verify(wordTranslationMapper, never()).toDTO(any(), isNull());
    }

    @Test
    void testGetAllPhrases_WithDifferentPageParameters() {
        // Given
        Integer pageNumber = 2;
        Integer pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        PhraseTranslation phrase = new PhraseTranslation();
        phrase.setId(1);

        List<PhraseTranslation> phrases = List.of(phrase);
        Page<PhraseTranslation> phrasePage = new PageImpl<>(phrases, pageable, phrases.size());

        PhraseTranslationDTO phraseDTO = new PhraseTranslationDTO();
        phraseDTO.setId(1);

        when(phraseTranslationRepository.findAll(pageable)).thenReturn(phrasePage);
        when(phraseTranslationMapper.toDTO(phrase, languageFr)).thenReturn(phraseDTO);

        // When
        List<PhraseTranslationDTO> result = phraseTranslationService.getAllTargetLanguagePhrases(pageNumber, pageSize, languageFr);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(phraseDTO, result.get(0));

        verify(phraseTranslationRepository, times(1)).findAll(pageable);
        verify(phraseTranslationMapper, times(1)).toDTO(phrase, languageFr);
    }
}
