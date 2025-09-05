package com.antonio.apprendrebackend.service.service;


import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.exception.PartSpeechFoundException;
import com.antonio.apprendrebackend.service.exception.WordNotFoundException;
import com.antonio.apprendrebackend.service.mapper.CategoryMapper;
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
import org.springframework.data.jpa.domain.Specification;

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
    private TenseService tenseService;

    @InjectMocks
    private WordServiceImpl wordService;
    @Mock
    private WordSenseService wordSenseService;
    @Mock
    private UserHistorialService userHistorialService;
    @Mock
    private WordSenseCategoryService wordSenseCategoryService;
    @Mock
    private WordSenseWithoutWordMapper wordSenseWithoutWordMapper;
    @Mock
    private WordMapper wordMapper;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private WordFilterRequestDTO wordFilterRequest;
    private PartSpeech verbPartSpeech;
    private Word verb1;
    private Word verb2;
    private WordDTO verbDTO1;
    private WordDTO verbDTO2;
    private WordSense wordSense1;
    private WordSense wordSense2;
    private WordSenseWithoutWordDTO wordSenseDTO1;
    private WordSenseWithoutWordDTO wordSenseDTO2;
    private UserHistorial userHistorial1;
    private UserHistorial userHistorial2;
    private Success success1;
    private Success success2;
    private Category category1;
    private CategoryDTO categoryDTO1;
    private WordSenseFilterRequestDTO wordSenseFilterRequest;
    private Pageable pageable;
    private Page<Word> wordPage;
    private Language language;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        verbPartSpeech = new PartSpeech();
        verbPartSpeech.setId(1);
        verbPartSpeech.setCode(PartSpeech.PartSpeechEnum.VERB);

        language = new Language();
        language.setCode(Language.LanguageEnum.FR);

        verb1 = new Word();
        verb1.setId(1);
        verb1.setName("run");
        verb1.setPartSpeech(verbPartSpeech);
        verb1.setLanguage(language);

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

        success1 = new Success();
        success1.setId(1);
        success1.setScore(1.0);

        success2 = new Success();
        success2.setId(2);
        success2.setScore(0.5);

        userHistorial1 = new UserHistorial();
        userHistorial1.setId(1);
        userHistorial1.setSuccess(success1);

        userHistorial2 = new UserHistorial();
        userHistorial2.setId(2);
        userHistorial2.setSuccess(success2);

        category1 = new Category();
        category1.setId(1);
        category1.setName("Animals");

        categoryDTO1 = new CategoryDTO();
        categoryDTO1.setName("Animals");

        wordSenseFilterRequest = new WordSenseFilterRequestDTO();

        pageable = PageRequest.of(0, 10);
        wordPage = new PageImpl<>(Arrays.asList(verb1, verb2));

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

        assertEquals("Not found any PartSpeech VERB", exception.getMessage());
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

    @Test
    void testGetWordWithSensePaginatedAplyingWordFilter_WithoutFilters() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Integer userId = 1;
        Language.LanguageEnum code = Language.LanguageEnum.FR;

        List<Word> words = Arrays.asList(verb1, verb2);
        List<UserHistorial> userHistorials1 = Arrays.asList(userHistorial1);
        List<UserHistorial> userHistorials2 = Arrays.asList(userHistorial2);

        doReturn(false).when(wordFilterRequest).hasAnyFilter();

        // When
        when(wordRepository.findByLanguageCode(
                eq(code),
                any(Specification.class),
                any(Pageable.class))
        ).thenReturn(new PageImpl<>(words));
        when(userHistorialService.getUserHistorialsByUserIdAndWordId(userId, verb1.getId()))
                .thenReturn(userHistorials1);
        when(userHistorialService.getUserHistorialsByUserIdAndWordId(userId, verb2.getId()))
                .thenReturn(userHistorials2);
        when(wordMapper.toDTO(verb1)).thenReturn(verbDTO1);
        when(wordMapper.toDTO(verb2)).thenReturn(verbDTO2);

        List<WordWithAttemptsAndSuccessDTO> result = wordService.getWordWithSensePaginatedByLanguageCodeAplyingWordFilter(
                pageNumber, pageSize, wordFilterRequest, userId, code);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getAttempts());
        assertEquals(1.0, result.get(0).getSuccess());
        assertEquals(1, result.get(1).getAttempts());
        assertEquals(0.5, result.get(1).getSuccess());
        verify(wordRepository, times(1)).findByLanguageCode(
                any(Language.LanguageEnum.class),
                any(Specification.class),
                any(Pageable.class));
        verify(userHistorialService, times(1)).getUserHistorialsByUserIdAndWordId(userId, verb1.getId());
        verify(userHistorialService, times(1)).getUserHistorialsByUserIdAndWordId(userId, verb2.getId());
    }

    @Test
    void testGetWordWithAttemptsAndSuccessPaginated() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Integer userId = 1;
        Language.LanguageEnum code = Language.LanguageEnum.FR;

        List<Word> words = Arrays.asList(verb1, verb2);
        List<UserHistorial> userHistorials1 = Arrays.asList(userHistorial1, userHistorial2);
        List<UserHistorial> userHistorials2 = Arrays.asList(userHistorial1);

        // When
        when(wordRepository.findByLanguageCode(eq(code), any(Pageable.class)))
                .thenReturn(new PageImpl<>(words));
        when(userHistorialService.getUserHistorialsByUserIdAndWordId(userId, verb1.getId())).thenReturn(userHistorials1);
        when(userHistorialService.getUserHistorialsByUserIdAndWordId(userId, verb2.getId())).thenReturn(userHistorials2);
        when(wordMapper.toDTO(verb1)).thenReturn(verbDTO1);
        when(wordMapper.toDTO(verb2)).thenReturn(verbDTO2);

        List<WordWithAttemptsAndSuccessDTO> result = wordService.getWordWithAttemptsAndSuccessPaginatedByLanguageCode(pageNumber, pageSize, userId, code);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getAttempts());
        assertEquals(1.5, result.get(0).getSuccess());
        assertEquals(1, result.get(1).getAttempts());
        assertEquals(1.0, result.get(1).getSuccess());
        verify(wordRepository, times(1)).findByLanguageCode(any(Language.LanguageEnum.class), any(Pageable.class));
        verify(userHistorialService, times(1)).getUserHistorialsByUserIdAndWordId(userId, verb1.getId());
        verify(userHistorialService, times(1)).getUserHistorialsByUserIdAndWordId(userId, verb2.getId());
    }

    @Test
    void testGetWordWithAttemptsAndSuccessPaginated_EmptyResults() {
        // Given
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Integer userId = 1;
        Language.LanguageEnum code = Language.LanguageEnum.FR;

        List<Word> emptyWords = new ArrayList<>();

        // When
        when(wordRepository.findByLanguageCode(eq(code), any(Pageable.class)))
                .thenReturn(new PageImpl<>(emptyWords));

        List<WordWithAttemptsAndSuccessDTO> result = wordService.getWordWithAttemptsAndSuccessPaginatedByLanguageCode(pageNumber, pageSize, userId, code);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordRepository, times(1)).findByLanguageCode(any(Language.LanguageEnum.class), any(Pageable.class));
        verify(userHistorialService, never()).getUserHistorialsByUserIdAndWordId(anyInt(), anyInt());
    }

    @Test
    void testGetWordSenseInfosWithoutWordByWordId() {
        // Given
        Integer wordId = 1;
        Integer userId = 1;

        List<WordSense> wordSenses = Arrays.asList(wordSense1, wordSense2);
        List<UserHistorial> userHistorials1 = Arrays.asList(userHistorial1, userHistorial2);
        List<UserHistorial> userHistorials2 = Arrays.asList(userHistorial1);
        List<Category> categories = Arrays.asList(category1);
        List<CategoryDTO> categoryDTOs = Arrays.asList(categoryDTO1);

        // When
        when(wordSenseService.getWordSensesByWordId(wordId)).thenReturn(wordSenses);
        when(userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordSense1.getId())).thenReturn(userHistorials1);
        when(userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordSense2.getId())).thenReturn(userHistorials2);
        when(wordSenseCategoryService.getCategoryByWordSenseId(wordSense1.getId())).thenReturn(categories);
        when(wordSenseCategoryService.getCategoryByWordSenseId(wordSense2.getId())).thenReturn(categories);
        when(categoryMapper.toDTO(category1)).thenReturn(categoryDTO1);
        when(wordSenseWithoutWordMapper.toDTO(wordSense1)).thenReturn(wordSenseDTO1);
        when(wordSenseWithoutWordMapper.toDTO(wordSense2)).thenReturn(wordSenseDTO2);

        List<WordSenseInfoWithoutWordDTO> result = wordService.getWordSenseInfosWithoutWordByWordId(wordId, userId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getAttempts());
        assertEquals(1.5, result.get(0).getSuccess());
        assertEquals(1, result.get(1).getAttempts());
        assertEquals(1.0, result.get(1).getSuccess());
        verify(wordSenseService, times(1)).getWordSensesByWordId(wordId);
        verify(userHistorialService, times(1)).getUserHistorialsByUserIdAndWordId(userId, wordSense1.getId());
        verify(userHistorialService, times(1)).getUserHistorialsByUserIdAndWordId(userId, wordSense2.getId());
        verify(wordSenseCategoryService, times(2)).getCategoryByWordSenseId(anyInt());
    }

    @Test
    void testGetWordSenseInfosWithoutWordByWordId_NoHistorial() {
        // Given
        Integer wordId = 1;
        Integer userId = 1;

        List<WordSense> wordSenses = Arrays.asList(wordSense1);
        List<UserHistorial> emptyHistorials = new ArrayList<>();
        List<Category> categories = Arrays.asList(category1);

        // When
        when(wordSenseService.getWordSensesByWordId(wordId)).thenReturn(wordSenses);
        when(userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordSense1.getId())).thenReturn(emptyHistorials);
        when(wordSenseCategoryService.getCategoryByWordSenseId(wordSense1.getId())).thenReturn(categories);
        when(categoryMapper.toDTO(category1)).thenReturn(categoryDTO1);
        when(wordSenseWithoutWordMapper.toDTO(wordSense1)).thenReturn(wordSenseDTO1);

        List<WordSenseInfoWithoutWordDTO> result = wordService.getWordSenseInfosWithoutWordByWordId(wordId, userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getAttempts());
        assertEquals(0.0, result.get(0).getSuccess());
        verify(wordSenseService, times(1)).getWordSensesByWordId(wordId);
        verify(userHistorialService, times(1)).getUserHistorialsByUserIdAndWordId(userId, wordSense1.getId());
    }

    @Test
    void testGetWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters_WithoutFilters() {
        // Given
        Integer wordId = 1;
        Integer userId = 1;
        wordSenseFilterRequest = mock(WordSenseFilterRequestDTO.class);

        List<WordSense> wordSenses = Arrays.asList(wordSense1);
        List<UserHistorial> userHistorials = Arrays.asList(userHistorial1);
        List<Category> categories = Arrays.asList(category1);

        // When
        when(wordSenseFilterRequest.hasAnyFilter()).thenReturn(false);
        when(wordSenseService.getWordSensesByWordId(wordId)).thenReturn(wordSenses);
        when(userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordSense1.getId())).thenReturn(userHistorials);
        when(wordSenseCategoryService.getCategoryByWordSenseId(wordSense1.getId())).thenReturn(categories);
        when(categoryMapper.toDTO(category1)).thenReturn(categoryDTO1);
        when(wordSenseWithoutWordMapper.toDTO(wordSense1)).thenReturn(wordSenseDTO1);

        List<WordSenseInfoWithoutWordDTO> result = wordService.getWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters(wordId, wordSenseFilterRequest, userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(wordSenseService, times(1)).getWordSensesByWordId(wordId);
        verify(wordSenseService, never()).getWordSensesByWordIdWithSpecification(any(), anyInt());
    }

    @Test
    void testGetWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters_WithFilters() {
        // Given
        Integer wordId = 1;
        Integer userId = 1;
        wordSenseFilterRequest = mock(WordSenseFilterRequestDTO.class);

        List<WordSense> wordSenses = Arrays.asList(wordSense1);
        List<UserHistorial> userHistorials = Arrays.asList(userHistorial1);
        List<Category> categories = Arrays.asList(category1);

        // When
        when(wordSenseFilterRequest.hasAnyFilter()).thenReturn(true);
        when(wordSenseService.getWordSensesByWordIdWithSpecification(any(), eq(wordId))).thenReturn(wordSenses);
        when(userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordSense1.getId())).thenReturn(userHistorials);
        when(wordSenseCategoryService.getCategoryByWordSenseId(wordSense1.getId())).thenReturn(categories);
        when(categoryMapper.toDTO(category1)).thenReturn(categoryDTO1);
        when(wordSenseWithoutWordMapper.toDTO(wordSense1)).thenReturn(wordSenseDTO1);

        List<WordSenseInfoWithoutWordDTO> result = wordService.getWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters(wordId, wordSenseFilterRequest, userId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getAttempts());
        assertEquals(1.0, result.get(0).getSuccess());
        verify(wordSenseService, times(1)).getWordSensesByWordIdWithSpecification(any(), eq(wordId));
        verify(wordSenseService, never()).getWordSensesByWordId(wordId);
    }

    @Test
    void testGetWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters_EmptyResults() {
        // Given
        Integer wordId = 1;
        Integer userId = 1;
        wordSenseFilterRequest = mock(WordSenseFilterRequestDTO.class);

        List<WordSense> emptyWordSenses = new ArrayList<>();

        // When
        when(wordSenseFilterRequest.hasAnyFilter()).thenReturn(true);
        when(wordSenseService.getWordSensesByWordIdWithSpecification(any(), eq(wordId))).thenReturn(emptyWordSenses);

        List<WordSenseInfoWithoutWordDTO> result = wordService.getWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters(wordId, wordSenseFilterRequest, userId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(wordSenseService, times(1)).getWordSensesByWordIdWithSpecification(any(), eq(wordId));
        verify(userHistorialService, never()).getUserHistorialsByUserIdAndWordId(anyInt(), anyInt());
    }
}
