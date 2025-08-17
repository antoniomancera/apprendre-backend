package com.antonio.apprendrebackend.service.service;


import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.exception.PartSpeechFoundException;
import com.antonio.apprendrebackend.service.exception.WordNotFoundException;
import com.antonio.apprendrebackend.service.mapper.WordMapper;
import com.antonio.apprendrebackend.service.mapper.WordSenseWithoutWordMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.model.Number;
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
    private PartSpeechService partSpeechService;

    @Mock
    private LevelService levelService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private PersonService personService;

    @Mock
    private GenderService genderService;

    @Mock
    private NumberService numberService;

    @Mock
    private MoodService moodService;

    @Mock
    private TenseService tenseService;

    @Mock
    private WordMapper wordMapper;

    @InjectMocks
    private WordServiceImpl wordService;
    @Mock
    private WordSenseService wordSenseService;

    @Mock
    private WordSenseWithoutWordMapper wordSenseWithoutWordMapper;


    private PartSpeech verbPartSpeech;
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

        verbPartSpeech = new PartSpeech();
        verbPartSpeech.setId(1);
        verbPartSpeech.setCode(PartSpeech.PartSpeechEnum.VERB);

        verb1 = new Word();
        verb1.setId(1);
        verb1.setName("run");
        verb1.setPartSpeech(verbPartSpeech);

        verb2 = new Word();
        verb2.setId(2);
        verb2.setName("jump");
        verb2.setPartSpeech(verbPartSpeech);

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
        when(partSpeechService.getByPartSpeech(PartSpeech.PartSpeechEnum.VERB)).thenReturn(verbPartSpeech);
        when(wordRepository.findByPartSpeech(verbPartSpeech)).thenReturn(verbList);
        when(wordMapper.toDTO(verb1)).thenReturn(verbDTO1);
        when(wordMapper.toDTO(verb2)).thenReturn(verbDTO2);

        List<WordDTO> result = wordService.getAllVerbs();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("run", result.get(0).getName());
        assertEquals("jump", result.get(1).getName());
        verify(partSpeechService, times(1)).getByPartSpeech(PartSpeech.PartSpeechEnum.VERB);
        verify(wordRepository, times(1)).findByPartSpeech(verbPartSpeech);
        verify(wordMapper, times(2)).toDTO(any(Word.class));
    }

    @Test
    void testGetAllVerbsEmpty() {
        // Given
        List<Word> emptyVerbList = new ArrayList<>();

        // When
        when(partSpeechService.getByPartSpeech(PartSpeech.PartSpeechEnum.VERB)).thenReturn(verbPartSpeech);
        when(wordRepository.findByPartSpeech(verbPartSpeech)).thenReturn(emptyVerbList);

        // Then
        WordNotFoundException exception = assertThrows(WordNotFoundException.class, () -> {
            wordService.getAllVerbs();
        });

        assertEquals("Not found any verb", exception.getMessage());
        verify(partSpeechService, times(1)).getByPartSpeech(PartSpeech.PartSpeechEnum.VERB);
        verify(wordRepository, times(1)).findByPartSpeech(verbPartSpeech);
        verify(wordMapper, never()).toDTO(any(Word.class));
    }

    @Test
    void testGetAllVerbsPartSpeechNotFound() {
        // Given
        PartSpeechFoundException partSpeechFoundException = new PartSpeechFoundException("Not found any PartSpeech VERB");

        // When
        when(partSpeechService.getByPartSpeech(PartSpeech.PartSpeechEnum.VERB)).thenThrow(partSpeechFoundException);

        // Then
        PartSpeechFoundException exception = assertThrows(PartSpeechFoundException.class, () -> {
            wordService.getAllVerbs();
        });

        assertEquals("Not found any part speech VERB", exception.getMessage());
        verify(partSpeechService, times(1)).getByPartSpeech(PartSpeech.PartSpeechEnum.VERB);
        verify(wordRepository, never()).findByPartSpeech(any(PartSpeech.class));
        verify(wordMapper, never()).toDTO(any(Word.class));
    }

    @Test
    void testGetAllVerbsSingleVerb() {
        // Given
        List<Word> singleVerbList = Arrays.asList(verb1);

        // When
        when(partSpeechService.getByPartSpeech(PartSpeech.PartSpeechEnum.VERB)).thenReturn(verbPartSpeech);
        when(wordRepository.findByPartSpeech(verbPartSpeech)).thenReturn(singleVerbList);
        when(wordMapper.toDTO(verb1)).thenReturn(verbDTO1);

        List<WordDTO> result = wordService.getAllVerbs();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("run", result.get(0).getName());
        verify(partSpeechService, times(1)).getByPartSpeech(PartSpeech.PartSpeechEnum.VERB);
        verify(wordRepository, times(1)).findByPartSpeech(verbPartSpeech);
        verify(wordMapper, times(1)).toDTO(verb1);
    }

    @Test
    void testGetAllVerbsMapperReturnsNull() {
        // Given
        List<Word> verbList = Arrays.asList(verb1);

        // When
        when(partSpeechService.getByPartSpeech(PartSpeech.PartSpeechEnum.VERB)).thenReturn(verbPartSpeech);
        when(wordRepository.findByPartSpeech(verbPartSpeech)).thenReturn(verbList);
        when(wordMapper.toDTO(verb1)).thenReturn(null);

        List<WordDTO> result = wordService.getAllVerbs();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0));
        verify(partSpeechService, times(1)).getByPartSpeech(PartSpeech.PartSpeechEnum.VERB);
        verify(wordRepository, times(1)).findByPartSpeech(verbPartSpeech);
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
        assertEquals(verb1.getPartSpeech(), result.getPartSpeech());
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

        WordWithSenseDTO firstWordWithSense = result.get(0);
        assertEquals("run", firstWordWithSense.getWord().getName());
        assertEquals(1, firstWordWithSense.getWordSenses().size());
        assertEquals(wordSenseDTO2.getId(), firstWordWithSense.getWordSenses().get(0).getId());

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
        Page<Word> wordPage = new PageImpl<>(words, pageable, 2);

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


    @Test
    void testGetAllWordFilterSuccess() {
        // Given
        PartSpeech partSpeech1 = new PartSpeech();
        partSpeech1.setId(1);
        partSpeech1.setCode(PartSpeech.PartSpeechEnum.SUSTANTIVE);
        List<PartSpeech> partSpeeches = Arrays.asList(partSpeech1);

        Level level1 = new Level();
        level1.setId(1);
        level1.setCode(Level.LevelEnum.A1);
        List<Level> levels = Arrays.asList(level1);

        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Animals");
        List<Category> categories = Arrays.asList(category1);

        Person person1 = new Person();
        person1.setId(1);
        person1.setCode("First Person");
        List<Person> persons = Arrays.asList(person1);

        Gender gender1 = new Gender();
        gender1.setId(1);
        gender1.setCode("Masculine");
        List<Gender> genders = Arrays.asList(gender1);

        com.antonio.apprendrebackend.service.model.Number number1 = new com.antonio.apprendrebackend.service.model.Number();
        number1.setId(1);
        number1.setCode("Singular");
        List<com.antonio.apprendrebackend.service.model.Number> numbers = Arrays.asList(number1);

        MoodWithTenseDTO moodWithTense1 = new MoodWithTenseDTO();
        List<MoodWithTenseDTO> moodWithTenses = Arrays.asList(moodWithTense1);

        // When
        when(partSpeechService.getAllPartSpeechs()).thenReturn(partSpeeches);
        when(levelService.getAllLevels()).thenReturn(levels);
        when(categoryService.getAllCategories()).thenReturn(categories);
        when(personService.getAllPersons()).thenReturn(persons);
        when(genderService.getAllGenders()).thenReturn(genders);
        when(numberService.getAllNumbers()).thenReturn(numbers);
        when(tenseService.getAllMoodWithTense()).thenReturn(moodWithTenses);

        WordFilterOptionsDTO result = wordService.getAllWordFilterOptions();

        // Then
        assertNotNull(result);
        assertNotNull(result.getPartSpeeches());
        assertNotNull(result.getLevels());
        assertNotNull(result.getCategories());
        assertNotNull(result.getPersons());
        assertNotNull(result.getGenders());
        assertNotNull(result.getNumbers());
        assertNotNull(result.getMoodWithTenses());

        assertEquals(1, result.getPartSpeeches().size());
        assertEquals(1, result.getLevels().size());
        assertEquals(1, result.getCategories().size());
        assertEquals(1, result.getPersons().size());
        assertEquals(1, result.getGenders().size());
        assertEquals(1, result.getNumbers().size());
        assertEquals(1, result.getMoodWithTenses().size());

        assertEquals(PartSpeech.PartSpeechEnum.SUSTANTIVE, result.getPartSpeeches().get(0).getCode());
        assertEquals(Level.LevelEnum.A1, result.getLevels().get(0).getCode());
        assertEquals("Animals", result.getCategories().get(0).getName());
        assertEquals("First Person", result.getPersons().get(0).getCode());
        assertEquals("Masculine", result.getGenders().get(0).getCode());
        assertEquals("Singular", result.getNumbers().get(0).getCode());

        verify(partSpeechService, times(1)).getAllPartSpeechs();
        verify(levelService, times(1)).getAllLevels();
        verify(categoryService, times(1)).getAllCategories();
        verify(personService, times(1)).getAllPersons();
        verify(genderService, times(1)).getAllGenders();
        verify(numberService, times(1)).getAllNumbers();
        verify(tenseService, times(1)).getAllMoodWithTense();
    }

    @Test
    void testGetAllWordSenseFilterWithEmptyLists() {
        // Given
        List<PartSpeech> emptyPartSpeeches = new ArrayList<>();
        List<Level> emptyLevels = new ArrayList<>();
        List<Category> emptyCategories = new ArrayList<>();
        List<Person> emptyPersons = new ArrayList<>();
        List<Gender> emptyGenders = new ArrayList<>();
        List<Number> emptyNumbers = new ArrayList<>();
        List<MoodWithTenseDTO> emptyMoodWithTenses = new ArrayList<>();

        // When
        when(partSpeechService.getAllPartSpeechs()).thenReturn(emptyPartSpeeches);
        when(levelService.getAllLevels()).thenReturn(emptyLevels);
        when(categoryService.getAllCategories()).thenReturn(emptyCategories);
        when(personService.getAllPersons()).thenReturn(emptyPersons);
        when(genderService.getAllGenders()).thenReturn(emptyGenders);
        when(numberService.getAllNumbers()).thenReturn(emptyNumbers);
        when(tenseService.getAllMoodWithTense()).thenReturn(emptyMoodWithTenses);

        WordFilterOptionsDTO result = wordService.getAllWordFilterOptions();

        // Then
        assertNotNull(result);
        assertTrue(result.getPartSpeeches().isEmpty());
        assertTrue(result.getLevels().isEmpty());
        assertTrue(result.getCategories().isEmpty());
        assertTrue(result.getPersons().isEmpty());
        assertTrue(result.getGenders().isEmpty());
        assertTrue(result.getNumbers().isEmpty());
        assertTrue(result.getMoodWithTenses().isEmpty());

        verify(partSpeechService, times(1)).getAllPartSpeechs();
        verify(levelService, times(1)).getAllLevels();
        verify(categoryService, times(1)).getAllCategories();
        verify(personService, times(1)).getAllPersons();
        verify(genderService, times(1)).getAllGenders();
        verify(numberService, times(1)).getAllNumbers();
        verify(tenseService, times(1)).getAllMoodWithTense();
    }

    @Test
    void testGetAllWordSenseFilterServicesCalledOnce() {
        // Given
        when(partSpeechService.getAllPartSpeechs()).thenReturn(new ArrayList<>());
        when(levelService.getAllLevels()).thenReturn(new ArrayList<>());
        when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());
        when(personService.getAllPersons()).thenReturn(new ArrayList<>());
        when(genderService.getAllGenders()).thenReturn(new ArrayList<>());
        when(numberService.getAllNumbers()).thenReturn(new ArrayList<>());
        when(tenseService.getAllMoodWithTense()).thenReturn(new ArrayList<>());

        // When
        wordService.getAllWordFilterOptions();

        // Then
        verify(partSpeechService, times(1)).getAllPartSpeechs();
        verify(levelService, times(1)).getAllLevels();
        verify(categoryService, times(1)).getAllCategories();
        verify(personService, times(1)).getAllPersons();
        verify(genderService, times(1)).getAllGenders();
        verify(numberService, times(1)).getAllNumbers();
        verify(tenseService, times(1)).getAllMoodWithTense();

        verifyNoMoreInteractions(partSpeechService, levelService, categoryService,
                personService, genderService, numberService, tenseService);
    }
}
