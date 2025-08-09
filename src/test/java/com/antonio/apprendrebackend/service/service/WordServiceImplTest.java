package com.antonio.apprendrebackend.service.service;


import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.dto.WordSenseWithoutWordDTO;
import com.antonio.apprendrebackend.service.dto.WordWithSenseDTO;
import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordNotFoundException;
import com.antonio.apprendrebackend.service.mapper.WordMapper;
import com.antonio.apprendrebackend.service.mapper.WordSenseWithoutWordMapper;
import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.model.Word;
import com.antonio.apprendrebackend.service.model.WordSense;
import com.antonio.apprendrebackend.service.repository.WordRepository;
import com.antonio.apprendrebackend.service.service.impl.WordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    @Mock
    private WordSenseService wordSenseService;

    @Mock
    private WordSenseWithoutWordMapper wordSenseWithoutWordMapper;


    private Type verbType;
    private Word verb1;
    private Word verb2;
    private WordDTO verbDTO1;
    private WordDTO verbDTO2;
    private WordSense wordSense1;
    private WordSense wordSense2;
    private WordSenseWithoutWordDTO wordSenseDTO1;
    private WordSenseWithoutWordDTO wordSenseDTO2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Common test data
        verbType = new Type();
        verbType.setId(1);
        verbType.setCode(Type.TypeEnum.VERB);

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

        wordSense1 = new WordSense();
        wordSense1.setId(1);

        wordSense2 = new WordSense();
        wordSense2.setId(2);

        wordSenseDTO1 = new WordSenseWithoutWordDTO();
        wordSenseDTO1.setId(1);

        wordSenseDTO2 = new WordSenseWithoutWordDTO();
        wordSenseDTO2.setId(2);
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

    @Test
    void testGetById_ReturnsWord() {
        // Given
        Integer wordId = 1;

        when(wordRepository.findById(wordId)).thenReturn(Optional.of(verb1));

        // When
        Word result = wordService.getWordById(wordId);

        // Then
        assertNotNull(result);
        assertEquals(verb1.getId(), result.getId());
        assertEquals(verb1.getName(), result.getName());
        assertEquals(verb1.getType(), result.getType());
        verify(wordRepository, times(1)).findById(wordId);
    }

    @Test
    void testGetById_ThrowsExceptionWhenNotFound() {
        // Given
        Integer wordId = 999;

        when(wordRepository.findById(wordId)).thenReturn(Optional.empty());

        // When / Then
        WordNotFoundException exception = assertThrows(
                WordNotFoundException.class,
                () -> wordService.getWordById(wordId)
        );

        assertEquals("Not found any word with id 999", exception.getMessage());
        verify(wordRepository, times(1)).findById(wordId);
    }

    @Test
    void testGetById_WithNullId() {
        // Given
        Integer wordId = null;

        when(wordRepository.findById(null)).thenReturn(Optional.empty());

        // When / Then
        WordNotFoundException exception = assertThrows(
                WordNotFoundException.class,
                () -> wordService.getWordById(wordId)
        );

        assertEquals("Not found any word with id null", exception.getMessage());
        verify(wordRepository, times(1)).findById(null);
    }

    @Test
    void testGetWordWithSensePaginated_ReturnsWordsWithSenses() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Word> words = Arrays.asList(verb1, verb2);
        Page<Word> wordPage = new PageImpl<>(words, pageable, words.size());

        List<WordSense> wordSenses1 = Arrays.asList(wordSense1);
        List<WordSense> wordSenses2 = Arrays.asList(wordSense2);

        when(wordRepository.findAll(pageable)).thenReturn(wordPage);
        when(wordMapper.toDTO(verb1)).thenReturn(verbDTO1);
        when(wordMapper.toDTO(verb2)).thenReturn(verbDTO2);
        when(wordSenseService.getWordSensesByWordId(verbDTO1.getId())).thenReturn(wordSenses1);
        when(wordSenseService.getWordSensesByWordId(verbDTO2.getId())).thenReturn(wordSenses2);
        when(wordSenseWithoutWordMapper.toDTO(wordSense1)).thenReturn(wordSenseDTO1);
        when(wordSenseWithoutWordMapper.toDTO(wordSense2)).thenReturn(wordSenseDTO2);

        // When
        List<WordWithSenseDTO> result = wordService.getWordWithSensePaginated(pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify first word with senses
        WordWithSenseDTO firstWordWithSense = result.get(0);
        assertEquals("run", firstWordWithSense.getWord().getName());
        assertEquals(1, firstWordWithSense.getWordSenses().size());
        assertEquals(wordSenseDTO2.getId(), firstWordWithSense.getWordSenses().get(0).getId());

        // Verify second word with senses
        WordWithSenseDTO secondWordWithSense = result.get(1);
        assertEquals("jump", secondWordWithSense.getWord().getName());
        assertEquals(1, secondWordWithSense.getWordSenses().size());

        verify(wordRepository, times(1)).findAll(pageable);
        verify(wordMapper, times(2)).toDTO(any(Word.class));
        verify(wordSenseService, times(2)).getWordSensesByWordId(any());
        verify(wordSenseWithoutWordMapper, times(2)).toDTO(any(WordSense.class));
    }

    @Test
    void testGetWordWithSensePaginated_EmptyPage() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Word> emptyWords = new ArrayList<>();
        Page<Word> emptyWordPage = new PageImpl<>(emptyWords, pageable, 0);

        when(wordRepository.findAll(pageable)).thenReturn(emptyWordPage);

        // When
        List<WordWithSenseDTO> result = wordService.getWordWithSensePaginated(pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(wordRepository, times(1)).findAll(pageable);
        verify(wordMapper, never()).toDTO(any(Word.class));
        verify(wordSenseService, never()).getWordSensesByWordId(any());
        verify(wordSenseWithoutWordMapper, never()).toDTO(any(WordSense.class));
    }

    @Test
    void testGetWordWithSensePaginated_WordWithoutSenses() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Word> words = Arrays.asList(verb1);
        Page<Word> wordPage = new PageImpl<>(words, pageable, words.size());

        List<WordSense> emptySenses = new ArrayList<>();

        when(wordRepository.findAll(pageable)).thenReturn(wordPage);
        when(wordMapper.toDTO(verb1)).thenReturn(verbDTO1);
        when(wordSenseService.getWordSensesByWordId(verbDTO1.getId())).thenReturn(emptySenses);

        // When
        List<WordWithSenseDTO> result = wordService.getWordWithSensePaginated(pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        WordWithSenseDTO wordWithSense = result.get(0);
        assertEquals("run", wordWithSense.getWord().getName());
        assertTrue(wordWithSense.getWordSenses().isEmpty());

        verify(wordRepository, times(1)).findAll(pageable);
        verify(wordMapper, times(1)).toDTO(verb1);
        verify(wordSenseService, times(1)).getWordSensesByWordId(verbDTO1.getId());
        verify(wordSenseWithoutWordMapper, never()).toDTO(any(WordSense.class));
    }

    @Test
    void testGetWordWithSensePaginated_WordWithMultipleSenses() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Word> words = Arrays.asList(verb1);
        Page<Word> wordPage = new PageImpl<>(words, pageable, words.size());

        List<WordSense> multipleSenses = Arrays.asList(wordSense1, wordSense2);

        when(wordRepository.findAll(pageable)).thenReturn(wordPage);
        when(wordMapper.toDTO(verb1)).thenReturn(verbDTO1);
        when(wordSenseService.getWordSensesByWordId(verbDTO1.getId())).thenReturn(multipleSenses);
        when(wordSenseWithoutWordMapper.toDTO(wordSense1)).thenReturn(wordSenseDTO1);
        when(wordSenseWithoutWordMapper.toDTO(wordSense2)).thenReturn(wordSenseDTO2);

        // When
        List<WordWithSenseDTO> result = wordService.getWordWithSensePaginated(pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        WordWithSenseDTO wordWithSense = result.get(0);
        assertEquals("run", wordWithSense.getWord().getName());
        assertEquals(2, wordWithSense.getWordSenses().size());
        assertEquals(wordSenseDTO1.getId(), wordWithSense.getWordSenses().get(0).getId());
        assertEquals(wordSenseDTO2.getId(), wordWithSense.getWordSenses().get(1).getId());

        verify(wordRepository, times(1)).findAll(pageable);
        verify(wordMapper, times(1)).toDTO(verb1);
        verify(wordSenseService, times(1)).getWordSensesByWordId(verbDTO1.getId());
        verify(wordSenseWithoutWordMapper, times(2)).toDTO(any(WordSense.class));
    }

    @Test
    void testGetWordWithSensePaginated_DifferentPageSizes() {
        // Given
        Integer pageNumber = 1;
        Integer pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Word> words = Arrays.asList(verb2);
        Page<Word> wordPage = new PageImpl<>(words, pageable, 2); // Total elements = 2

        List<WordSense> wordSenses = Arrays.asList(wordSense2);

        when(wordRepository.findAll(pageable)).thenReturn(wordPage);
        when(wordMapper.toDTO(verb2)).thenReturn(verbDTO2);
        when(wordSenseService.getWordSensesByWordId(verbDTO2.getId())).thenReturn(wordSenses);
        when(wordSenseWithoutWordMapper.toDTO(wordSense2)).thenReturn(wordSenseDTO2);

        // When
        List<WordWithSenseDTO> result = wordService.getWordWithSensePaginated(pageNumber, pageSize);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("jump", result.get(0).getWord().getName());

        verify(wordRepository, times(1)).findAll(pageable);
    }
}
