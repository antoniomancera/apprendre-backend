package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.exception.PartSpeechFoundException;
import com.antonio.apprendrebackend.service.exception.WordNotFoundException;
import com.antonio.apprendrebackend.service.mapper.CategoryMapper;
import com.antonio.apprendrebackend.service.mapper.WordMapper;
import com.antonio.apprendrebackend.service.mapper.WordSenseWithoutWordMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.model.Number;
import com.antonio.apprendrebackend.service.repository.WordRepository;
import com.antonio.apprendrebackend.service.repository.specification.WordSenseSpecification;
import com.antonio.apprendrebackend.service.repository.specification.WordSpecification;
import com.antonio.apprendrebackend.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WordServiceImpl implements WordService {
    private static final Logger logger = LoggerFactory.getLogger(WordServiceImpl.class);
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private PartSpeechService partSpeechService;
    @Autowired
    private WordSenseService wordSenseService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PersonService personService;
    @Autowired
    private GenderService genderService;
    @Autowired
    private NumberService numberService;
    @Autowired
    private MoodService moodService;
    @Autowired
    private TenseService tenseService;
    @Autowired
    private UserHistorialService userHistorialService;
    @Autowired
    private WordSenseCategoryService wordSenseCategoryService;
    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private WordSenseWithoutWordMapper wordSenseWithoutWordMapper;
    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * Get a word by their Id if exists
     *
     * @param wordId
     * @return Word
     * @throws WordNotFoundException
     */
    @Override
    public Word getWordById(Integer wordId) {
        logger.debug("Called getWordById() in WordService for word-{}", wordId);

        return wordRepository.findById(wordId).orElseThrow(() -> new WordNotFoundException(String.format("Not found any word with id %s", wordId)));
    }


    /**
     * Get all the words that are verbs
     *
     * @return List<WordDTO>
     * @throws PartSpeechFoundException if not exist Verb as a part of speech
     * @throws WordNotFoundException    if not exist any Verb
     */
    @Override
    public List<WordDTO> getAllVerbs() {
        logger.debug("Called getAllVerbs() in WordService");

        PartSpeech partSpeech = partSpeechService.getByPartSpeech(PartSpeech.PartSpeechEnum.VERB);
        List<Word> verbs = wordRepository.findByPartSpeech(partSpeech);
        if (verbs.isEmpty()) {
            throw new WordNotFoundException("Not found any verb");
        }
        return verbs.stream()
                .map(wordMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Return a page with wordWitSenses, that is a collection of word with their respective wordSense
     *
     * @param pageNumber
     * @param pageSize
     * @return a List<WordWithSenseDTO>
     */
 /*   @Override
    public List<WordWithSenseDTO> getWordWithSensePaginated(Integer pageNumber, Integer pageSize) {
        logger.debug("Get the page: %d of WordWithSenseDTO with size: %", pageNumber, pageSize);

        List<WordWithSenseDTO> wordWithSenseDTOS = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<WordDTO> words = wordRepository
                .findAll(pageable).stream().map(wordMapper::toDTO).collect(Collectors.toList());

        words.forEach((word) -> {
            wordWithSenseDTOS.add(new WordWithSenseDTO(word, wordSenseService.getWordSensesByWordId(word.getId()).stream().map(wordSenseWithoutWordMapper::toDTO).collect(Collectors.toList())));
        });
        return wordWithSenseDTOS;
    }

  */

    /**
     * Get a list with all the parameters availables to filter for word and wordSense, that are;
     * for all level, category, part of speech; for part of speech variables  person, gender, number;
     * and finally for part of speech with conjugation mood and tense
     *
     * @return WordFilterOptionsDTO
     */
    @Override
    public WordFilterOptionsDTO getAllWordFilterOptions() {
        logger.debug("Called getAllWordFilterOptions() in WordService");

        List<PartSpeech> partSpeeches = partSpeechService.getAllPartSpeechs();
        List<Level> levels = levelService.getAllLevels();
        List<Category> categories = categoryService.getAllCategories();
        List<Person> persons = personService.getAllPersons();
        List<Gender> genders = genderService.getAllGenders();
        List<Number> numbers = numberService.getAllNumbers();
        List<MoodWithTenseDTO> moodWithTenses = tenseService.getAllMoodWithTense();

        return new WordFilterOptionsDTO(partSpeeches, levels, categories, persons, genders, numbers, moodWithTenses);
    }

    /**
     * Returns a page of WordWithAttemptsAndSuccess applying filter if exists, that is a list of Words with their number
     * of attempts and accuracy
     *
     * @param pageNumber
     * @param pageSize
     * @param wordFilterRequest
     * @return List<WordWithAttemptsAndSuccessDTO>
     */
    @Override
    public List<WordWithAttemptsAndSuccessDTO> getWordWithSensePaginatedAplyingWordFilter(Integer pageNumber, Integer pageSize, WordFilterRequestDTO wordFilterRequest, Integer userId) {
        logger.debug("Called getWordWithSensePaginatedAplyingWordFilter() in WordService for pageSize-{}, and pageNumber-{}", pageSize, pageNumber);

        if (wordFilterRequest.hasAnyFilter()) {
            getWordWithAttemptsAndSuccessPaginated(pageNumber, pageSize, userId);
        }

        Specification<Word> spec = WordSpecification.withFilters(wordFilterRequest);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Word> words = wordRepository.findAll(spec, pageable).getContent().stream().collect(Collectors.toList());

        return getAttemptsAndSuccessesByWords(words, userId);
    }

    /**
     * Returns a page of WordWithAttemptsAndSuccess, that is a list of Words with their number
     * of attempts and accuracy
     *
     * @param pageNumber
     * @param pageSize
     * @return List<WordWithAttemptsAndSuccessDTO>
     */
    @Override
    public List<WordWithAttemptsAndSuccessDTO> getWordWithAttemptsAndSuccessPaginated(Integer pageNumber, Integer pageSize, Integer userId) {
        logger.info("Called getWordWithAttemptsAndSuccessPaginated() in WordService for pageSize-{}, and pageNumber-{}", pageSize, pageNumber);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Word> words = wordRepository.findAll(pageable).getContent().stream().collect(Collectors.toList());

        return getAttemptsAndSuccessesByWords(words, userId);
    }

    /**
     * Get the senses with Info of a word
     *
     * @param wordId
     * @return List<WordSenseInfoWithoutWordDTO>
     */
    @Override
    public List<WordSenseInfoWithoutWordDTO> getWordSenseInfosWithoutWordByWordId(Integer wordId, Integer userId) {
        logger.debug("Called getWordSenseInfosWithoutWordByWordId() in WordService for wordId-{}, and pageNumber-{}", wordId);

        List<WordSenseInfoWithoutWordDTO> wordSenseInfoWithoutWords = new ArrayList<>();
        List<WordSense> wordSenses = wordSenseService.getWordSensesByWordId(wordId);

        for (WordSense wordSense : wordSenses) {
            List<UserHistorial> userHistorials = userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordSense.getId());

            Double success = 0.0;
            Integer attempts = 0;

            for (UserHistorial userHistorial : userHistorials) {
                attempts += 1;
                success += userHistorial.getSuccess().getScore();
            }

            List<CategoryDTO> categories = wordSenseCategoryService.getCategoryByWordSenseId(wordSense.getId()).stream().map(category -> categoryMapper.toDTO(category)).collect(Collectors.toList());
            wordSenseInfoWithoutWords.add(new WordSenseInfoWithoutWordDTO(wordSenseWithoutWordMapper.toDTO(wordSense), attempts, success, categories));
        }
        return wordSenseInfoWithoutWords;
    }

    /**
     * Get the senses with Info of a word on applying filters if exists
     *
     * @param wordId
     * @param wordSenseFilterRequest
     * @return List<WordSenseInfoWithoutWordDTO>
     */
    @Override
    public List<WordSenseInfoWithoutWordDTO> getWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters(Integer wordId, WordSenseFilterRequestDTO wordSenseFilterRequest, Integer userId) {
        logger.info("Called getWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters() in WordService for wordId-{}, and pageNumber-{}", wordId);

        if (!wordSenseFilterRequest.hasAnyFilter()) {
            return getWordSenseInfosWithoutWordByWordId(wordId, userId);
        }
        List<WordSenseInfoWithoutWordDTO> wordSenseInfoWithoutWords = new ArrayList<>();
        Specification<WordSense> spec = WordSenseSpecification.withFilters(wordSenseFilterRequest);
        List<WordSense> wordSenses = wordSenseService.getWordSensesByWordIdWithSpecification(spec, wordId);

        for (WordSense wordSense : wordSenses) {
            List<UserHistorial> userHistorials = userHistorialService.getUserHistorialsByUserIdAndWordId(userId, wordSense.getId());

            Double success = 0.0;
            Integer attempts = 0;

            for (UserHistorial userHistorial : userHistorials) {
                attempts += 1;
                success += userHistorial.getSuccess().getScore();
            }

            List<CategoryDTO> categories = wordSenseCategoryService.getCategoryByWordSenseId(wordSense.getId()).stream().map(category -> categoryMapper.toDTO(category)).collect(Collectors.toList());
            wordSenseInfoWithoutWords.add(new WordSenseInfoWithoutWordDTO(wordSenseWithoutWordMapper.toDTO(wordSense), attempts, success, categories));
        }
        return wordSenseInfoWithoutWords;
    }


    private List<WordWithAttemptsAndSuccessDTO> getAttemptsAndSuccessesByWords(List<Word> words, Integer userId) {
        List<WordWithAttemptsAndSuccessDTO> wordWithAttemptsAndSuccesses = new ArrayList<>();

        for (Word word : words) {
            List<UserHistorial> userHistorials = userHistorialService.getUserHistorialsByUserIdAndWordId(userId, word.getId());
            Double success = 0.0;
            Integer attempts = 0;

            for (UserHistorial userHistorial : userHistorials) {
                attempts += 1;
                success += userHistorial.getSuccess().getScore();
            }

            wordWithAttemptsAndSuccesses.add(new WordWithAttemptsAndSuccessDTO(wordMapper.toDTO(word), attempts, success));
        }

        return wordWithAttemptsAndSuccesses;
    }
}