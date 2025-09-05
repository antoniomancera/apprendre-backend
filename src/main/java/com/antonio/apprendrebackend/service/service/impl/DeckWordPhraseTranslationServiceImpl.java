package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.exception.DeckWordPhraseTranslationNotFoundException;
import com.antonio.apprendrebackend.service.mapper.*;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.DeckWordPhraseTranslationRespository;
import com.antonio.apprendrebackend.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeckWordPhraseTranslationServiceImpl implements DeckWordPhraseTranslationService {
    private static final Logger logger = LoggerFactory.getLogger(DeckWordPhraseTranslationServiceImpl.class);

    @Autowired
    private DeckWordPhraseTranslationRespository deckWordPhraseTranslationRespository;

    @Autowired
    private DeckService deckService;

    @Autowired
    private WordPhraseTranslationService wordPhraseTranslationService;

    @Autowired
    private UserHistorialService userHistorialService;

    @Autowired
    private SuccessService successService;

    @Autowired
    private DeckMapper deckMapper;

    @Autowired
    private WordSenseCategoryService wordSenseCategoryService;

    @Autowired
    private WordService wordService;

    @Autowired
    private WordSenseService wordSenseService;

    @Autowired
    private WordPhraseTranslationMapper wordPhraseTranslationMapper;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private WordSenseWithoutWordMapper wordSenseWithoutWordMapper;


    /**
     * Return a random DeckWordPhraseTranslation of an user
     *
     * @param userId
     * @return DeckWordPhraseTranslation
     */
    @Override
    public DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByUser(Integer userId) {
        logger.debug(String.format("Get a random DeckWorPhraseTranslation for userId: %d", userId));

        Optional<DeckWordPhraseTranslation> deckWordPhraseTranslation = deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByUser(userId);
        if (deckWordPhraseTranslation.isEmpty()) {
            throw new DeckWordPhraseTranslationNotFoundException(String.format("Word not found for deck"));
        }

        return deckWordPhraseTranslation.get();
    }

    /**
     * Return a random DeckWordPhraseTranslation from a deck
     *
     * @param deckId
     * @return DeckWordPhraseTranslation
     */
    @Override
    public DeckWordPhraseTranslation getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(Integer deckId) {
        logger.debug(String.format("Get a random DeckWorPhraseTranslation for deckId: %d", deckId));

        Optional<DeckWordPhraseTranslation> deckWordPhraseTranslation = deckWordPhraseTranslationRespository.findRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
        if (deckWordPhraseTranslation.isEmpty()) {
            throw new DeckWordPhraseTranslationNotFoundException(String.format("Word not found for deck", deckId));
        }
        return deckWordPhraseTranslation.get();
    }

    /**
     * Get DeckWordPhraseTranslation given a Deck and a WordPhraseTranslation
     *
     * @param deckId
     * @param wordPhraseTranslationId
     * @return a DeckWordPhraseTranslation
     * @throws DeckWordPhraseTranslationNotFoundException
     */
    @Override
    public DeckWordPhraseTranslation getByDeckIdAndWordPhraseTranslationId(Integer deckId, Integer wordPhraseTranslationId) {
        logger.debug(String.format("Get the DeckWordPhraseTranslation of deckId: %d and wordPhraseTranslationId: %d", deckId, wordPhraseTranslationId));

        return deckWordPhraseTranslationRespository
                .findByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseTranslationId)
                .orElseThrow(() -> new DeckWordPhraseTranslationNotFoundException(
                        String.format(
                                "DeckWordPhraseTranslation not found for deckId %d and wordPhraseTranslationId %d",
                                deckId,
                                wordPhraseTranslationId
                        )
                ));
    }

    /**
     * Return all WordTranslation of a deck
     *
     * @param deckId
     * @return List<WordTranslation>
     */
    @Override
    public List<WordTranslation> getWordTranslationsByDeckId(Integer deckId) {
        logger.debug(String.format("Get the list of wordTranslations of the deck: %d", deckId));

        return deckWordPhraseTranslationRespository.findWordTranslationsByDeckId(deckId);
    }

    /**
     * Return all PhraseTranslation of a deck
     *
     * @param deckId
     * @return List<PhraseTranslation>
     */
    @Override
    public List<PhraseTranslation> getPhraseTranslationsByDeckId(Integer deckId) {
        logger.debug(String.format("Get the list of prhaseTranslations of the deck: %d", deckId));

        return deckWordPhraseTranslationRespository.findPhraseTranslationsByDeckId(deckId);
    }

    /**
     * Attempt a WordPhraseTranslation, will add an userHistorial depending on the result of the attemps, besides returns a WordTranslation
     * in case of success
     *
     * @param wordPhraseId
     * @param deckId
     * @param attempt
     * @return AttemptResultDTO with result and new WordPhraseTranslation in case of success
     * @throws DeckWordPhraseTranslationNotFoundException if not exist anyone
     */
    @Override
    public AttemptResultDTO attemptsWordPhraseTranslation(UserInfo userInfo, Integer wordPhraseId, Integer deckId, String attempt) {
        logger.debug("Called attemptsWordPhraseTranslation() in DeckWordPhraseTranslationService for deck-{}, attempt-{}, and wordPhrase", deckId, attempt, wordPhraseId);

        DeckWordPhraseTranslation deckWordPhraseTranslation = getByDeckIdAndWordPhraseTranslationId(deckId, wordPhraseId);
        Deck deck = deckService.getDeckbyId(deckId);
        Boolean hasSuccess = deckWordPhraseTranslation.getWordPhraseTranslation().getWordTranslation().getWordSenseA().getWord().getName().equals(attempt);
        updateStats(hasSuccess, deckWordPhraseTranslation);
        userHistorialService.postUserHistorial(new UserHistorial(deckWordPhraseTranslation, userInfo, successService.getSuccessByAttemptAndWordSense(attempt, deckWordPhraseTranslation.getWordPhraseTranslation().getWordTranslation().getWordSenseA()), deck));
        if (hasSuccess) {
            return new AttemptResultDTO(true, getRandomWordPhraseTranslation(userInfo, deckId));
        }
        return new AttemptResultDTO(false, null);
    }

    /**
     * Create a deck with a name and a description, besides, a list of deckWordPhraseTranslation is created linked
     * to the deck
     *
     * @param userInfo
     * @param name
     * @param description
     * @param wordPhraseTranslationIds
     * @return DeckDTO created
     * @throws WordPhraseTranslationNotFoundException if any wordPhraseTranslation is not found
     */
    @Override
    public DeckDTO createDeckWithWordPhraseTranslation(UserInfo userInfo, String name, String description, List<Integer> wordPhraseTranslationIds) {
        logger.debug("Called createDeckWithWordPhraseTranslation() in DeckWordPhraseTranslationService for requestName-{}, with description-{}, and wordPhraseTranslationIds", name, description, wordPhraseTranslationIds);

        Deck deck = deckService.createDeck(new Deck(userInfo, name, description));
        for (Integer id : wordPhraseTranslationIds) {
            deckWordPhraseTranslationRespository.save(new DeckWordPhraseTranslation(deck, wordPhraseTranslationService.getWordPhraseTranslationById(id)));
        }

        return deckMapper.toDTO(deck);
    }

    /**
     * Returns a page of WordWithAttemptsAndSuccess of a deck, that is a list of Words with their number of ssucces
     * and accuracy in a deck
     *
     * @param deckId
     * @param pageNumber
     * @param pageSize
     * @return List<WordWithAttemptsAndSuccessDTO>
     */
    @Override
    public List<WordWithAttemptsAndSuccessDTO> getWordWithAttemptsAndSuccessPaginatedByDeckId(Integer deckId, Integer pageNumber, Integer pageSize, Integer userId) {
        logger.debug("Called getWordsWithAttemptsAndSuccessPaginatedByDeckId() in DeckWordPhraseTranslationService for deck-{}, pageNumber-{}, and pageSize-{}", deckId, pageNumber, pageSize);

        List<WordWithAttemptsAndSuccessDTO> wordWithAttemptsAndSuccesses = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<DeckWordPhraseTranslation> deckWordPhraseTranslations = deckWordPhraseTranslationRespository.findByDeckId(pageable, deckId);

        List<Word> words = deckWordPhraseTranslations.stream()
                .map(deckWordPhraseTranslation -> deckWordPhraseTranslation.getWordPhraseTranslation().getWordTranslation().getWordSenseA().getWord())
                .distinct()
                .collect(Collectors.toList());

        for (Word word : words) {
            List<UserHistorial> userHistorials = userHistorialService.getUserHistorialsByDeckIdAndWordId(deckId, word.getId());

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

    /**
     * Returns the senses of a Word alongside all their categories number of attempts(in  general) and accuracy
     *
     * @param deckId
     * @param wordId
     * @return List<WordSenseInfoWithoutWordDTO>
     */
    @Override
    public List<WordSenseInfoWithoutWordDTO> getWordSenseInfosWithoutWordByWordIdAndDeckId(Integer deckId, Integer wordId) {
        logger.debug("Called getWordSenseInfosWithoutWordByWordIdAndDeckId() in DeckWordPhraseTranslationService for deck-{}, and word-{}", deckId, wordId);

        List<WordSenseInfoWithoutWordDTO> wordSenseInfoWithoutWords = new ArrayList<>();

        List<WordSense> wordSenses = wordSenseService.getWordSensesByWordIdAndDeckId(wordId, deckId);
        for (WordSense wordSense : wordSenses) {
            List<UserHistorial> userHistorials = userHistorialService.getUserHistorialsByDeckIdAndWordSenseId(deckId, wordSense.getId());

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
     * Return a map with the words and wordSenses already in the deck, alongside the first page of a WordWithAttemptsAndSuccesses
     *
     * @param deckId
     * @param pageSize
     * @return DeckEditInitInfoDTO
     */
    @Override
    public DeckEditInitInfoDTO getDeckEditInitInfo(Integer deckId, Integer pageSize, Integer userId) {
        logger.debug("Called getDeckEditInit() in DeckWordPhraseTranslationService for deck-{}, and pageSize-{}", deckId, pageSize);

        List<WordSense> wordSenses = wordSenseService.getTargetLanguageWordSensesByDeckId(deckId);

        Map<Integer, List<Integer>> wordToWordSensesIdMap = wordSenses.stream()
                .collect(Collectors.groupingBy(
                        wordSense -> wordSense.getWord().getId(),
                        Collectors.mapping(WordSense::getId, Collectors.toList())
                ));

        return new DeckEditInitInfoDTO(wordToWordSensesIdMap, wordService.getWordWithAttemptsAndSuccessPaginatedByLanguageCode(0, pageSize, userId, deckService.getDeckbyId(deckId).getCourse().getTargetLanguage().getCode()));
    }

    /**
     * Return a Random WordPhraseTranslationDTO depending on an optional deck
     *
     * @param deckId
     * @return WordPhraseTranslationDTO
     */
    @Override
    public WordPhraseTranslationDTO getRandomWordPhraseTranslation(UserInfo userInfo, Integer deckId) {
        logger.debug("Called getRandomWordPhraseTranslation() in DeckWordPhraseTranslationService for deck-{}", deckId);

        DeckWordPhraseTranslation deckWordTranslation;
        Deck deck = deckService.getDeckbyId(deckId);
        if (deckId != null) {
            deckWordTranslation = getRandomUserDeckWordPhraseTranslationWithByDeckAndUser(deckId);
        } else {
            deckWordTranslation = getRandomUserDeckWordPhraseTranslationWithByUser(userInfo.getId());
        }
        return wordPhraseTranslationMapper.toDTO(deckWordTranslation.getWordPhraseTranslation(), deck.getCourse().getTargetLanguage());
    }

    private static void updateStats(boolean success, DeckWordPhraseTranslation deckWordPhraseTranslation) {
        deckWordPhraseTranslation.setAttempts(deckWordPhraseTranslation.getAttempts() + 1);
        if (success) {
            deckWordPhraseTranslation.setSuccesses(deckWordPhraseTranslation.getSuccesses() + 1);
        }
    }
}
