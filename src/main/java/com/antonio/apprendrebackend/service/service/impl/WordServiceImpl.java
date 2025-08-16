package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.MoodWithTenseDTO;
import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.dto.WordFilterOptionsDTO;
import com.antonio.apprendrebackend.service.dto.WordWithSenseDTO;
import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordNotFoundException;
import com.antonio.apprendrebackend.service.mapper.WordMapper;
import com.antonio.apprendrebackend.service.mapper.WordSenseWithoutWordMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.model.Number;
import com.antonio.apprendrebackend.service.repository.WordRepository;
import com.antonio.apprendrebackend.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private TypeService typeService;
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
    private WordMapper wordMapper;
    @Autowired
    private WordSenseWithoutWordMapper wordSenseWithoutWordMapper;


    /**
     * Get a word by their Id if exists
     *
     * @param wordId
     * @return Word
     * @throws WordNotFoundException
     */
    @Override
    public Word getWordById(Integer wordId) {
        logger.debug(String.format("Get the word with id: %d", wordId));

        return wordRepository.findById(wordId).orElseThrow(() -> new WordNotFoundException(String.format("Not found any word with id %s", wordId)));
    }


    /**
     * Get all the words that are verbs
     *
     * @return List<WordDTO>
     * @throws TypeNotFoundException if not exist Verb as a type
     * @throws WordNotFoundException if not exist any Verb
     */
    @Override
    public List<WordDTO> getAllVerbs() {
        logger.debug("Get a list with all the verbs");

        Type type = typeService.getByType(Type.TypeEnum.VERB);
        List<Word> verbs = wordRepository.findByType(type);
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
    @Override
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

    /**
     * Get a list with all the parameters availables to filter for word and wordSense, that are;
     * for all level, category, part of speech; for part of speech variables  person, gender, number;
     * and finally for part of speech with conjugation mood and tense
     *
     * @return WordFilterOptionsDTO
     */
    @Override
    public WordFilterOptionsDTO getAllWordFilterOptions() {
        List<Type> types = typeService.getAllTypes();
        List<Level> levels = levelService.getAllLevels();
        List<Category> categories = categoryService.getAllCategories();
        List<Person> persons = personService.getAllPersons();
        List<Gender> genders = genderService.getAllGenders();
        List<Number> numbers = numberService.getAllNumbers();
        List<MoodWithTenseDTO> moodWithTenses = tenseService.getAllMoodWithTense();

        return new WordFilterOptionsDTO(types, levels, categories, persons, genders, numbers, moodWithTenses);
    }

    @Override
    public List<WordWithSenseDTO> getWordWithSensePaginatedAplyingWordSenseFilter(Integer pageNumber, Integer pageSize, WordFilterOptionsDTO wordFilterOptionsDTO) {
        List<WordWithSenseDTO> wordWithSenseDTOS = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);


        return getWordWithSensePaginated(pageNumber, pageSize);
    }
}