package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.exception.PartSpeechFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/word")
public class WordController {
    private static final Logger logger = LoggerFactory.getLogger(WordController.class);
    @Autowired
    WordService wordService;

    /**
     * Get all the words that are verbs
     *
     * @return HTTP respond with a List<WordDTO>
     * @throws PartSpeechFoundException if not exist Verb as a type
     * @throws WordNotFoundException    if not exist any Verb
     */
    @GetMapping(path = "/allVerbs")
    public @ResponseBody ResponseEntity<List<WordDTO>> getAllVerbs() {
        logger.info("Called getAllVerbs() in WordController");

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordDTO> verbs = wordService.getAllVerbs();
        return ResponseEntity.ok(verbs);
    }

    /**
     * Get a list with all the parameters availables to filter for word and wordSense, that are;
     * for all level, category, part of speech; for part of speech variables  person, gender, number;
     * and finally for part of speech with conjugation mood and tense
     *
     * @return HTTP respond with WordFilterOptionsDTO
     */
    @GetMapping(path = "/allFilters/")
    public @ResponseBody ResponseEntity<WordFilterOptionsDTO> getAllWordFilterOptions(

    ) {
        logger.info("Called getAllWordFilterOptions() in WordController");

        SecurityContextHolder.getContext().getAuthentication().getCredentials();
        WordFilterOptionsDTO wordSenseFilters = wordService.getAllWordFilterOptions();
        return ResponseEntity.ok(wordSenseFilters);
    }

    /**
     * Returns a page of WordWithAttemptsAndSuccess applying filter if exists, that is a list of Words with their number
     * of attempts and accuracy
     *
     * @param pageNumber
     * @param pageSize
     * @param wordFilterRequest
     * @return HTTP respond with a List<WordWithAttemptsAndSuccessDTO>
     */
    @PostMapping(path = "applyFilters/paginated/{pageNumber}/{pageSize}")
    public @ResponseBody ResponseEntity<List<WordWithAttemptsAndSuccessDTO>> getWordWithSensePaginatedByLanguageCodeAplyingWordFilter(
            @PathVariable int pageNumber,
            @PathVariable int pageSize,
            @RequestBody WordFilterRequestDTO wordFilterRequest
    ) {
        logger.info("Called getWordWithSensePaginatedByLanguageCodeAplyingWordFilter() in WordController for pageSize-{}, and pageNumber-{}", pageSize, pageNumber);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordWithAttemptsAndSuccessDTO> words = wordService.getWordWithSensePaginatedByLanguageCodeAplyingWordFilter(pageNumber, pageSize, wordFilterRequest, userInfo.getId(), userInfo.getCurrentCourse().getTargetLanguage().getCode());
        return ResponseEntity.ok(words);
    }

    /**
     * Returns a page of WordWithAttemptsAndSuccess, that is a list of Words with their number
     * of attempts and accuracy
     *
     * @param pageNumber
     * @param pageSize
     * @return HTTP respond with a List<WordWithAttemptsAndSuccessDTO>
     */
    @GetMapping(path = "withAttemptsAndSuccesses/paginated/{pageNumber}/{pageSize}")
    public @ResponseBody ResponseEntity<List<WordWithAttemptsAndSuccessDTO>> getWordWithAttemptsAndSuccessPaginatedByLanguageCode(
            @PathVariable int pageNumber,
            @PathVariable int pageSize
    ) {
        logger.info("Called getWordWithAttemptsAndSuccessPaginatedByLanguageCode() in WordController for pageSize-{}, and pageNumber-{}", pageSize, pageNumber);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordWithAttemptsAndSuccessDTO> wordWithAttemptsAndSuccesses = wordService.getWordWithAttemptsAndSuccessPaginatedByLanguageCode(pageNumber, pageSize, userInfo.getId(), userInfo.getCurrentCourse().getTargetLanguage().getCode());
        return ResponseEntity.ok(wordWithAttemptsAndSuccesses);
    }

    /**
     * Get the senses with Info of a word
     *
     * @param wordId
     * @return HTTP respond with a List<WordSenseInfoWithoutWordDTO>
     */
    @GetMapping(path = "wordSenseInfo/{wordId}")
    public @ResponseBody ResponseEntity<List<WordSenseInfoWithoutWordDTO>> getWordSenseInfosWithoutWordByWordId(
            @PathVariable int wordId
    ) {
        logger.info("Called getWordSenseInfosWithoutWordByWordId() in WordController for wordId-{}, and pageNumber-{}", wordId);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordSenseInfoWithoutWordDTO> wordWithAttemptsAndSuccesses = wordService.getWordSenseInfosWithoutWordByWordId(wordId, userInfo.getId());
        return ResponseEntity.ok(wordWithAttemptsAndSuccesses);
    }

    /**
     * Get the senses with Info of a word on applying filters if exists
     *
     * @param wordId
     * @param wordSenseFilterRequest
     * @return HTTP respond with a List<WordSenseInfoWithoutWordDTO>
     */
    @PostMapping(path = "wordSenseInfo/applyFilters/{wordId}")
    public @ResponseBody ResponseEntity<List<WordSenseInfoWithoutWordDTO>> getWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters(
            @PathVariable int wordId,
            @RequestBody WordSenseFilterRequestDTO wordSenseFilterRequest
    ) {
        logger.info("Called getWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters() in WordController for wordId-{}, and pageNumber-{}", wordId);

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        List<WordSenseInfoWithoutWordDTO> wordWithAttemptsAndSuccesses = wordService.getWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters(wordId, wordSenseFilterRequest, userInfo.getId());
        return ResponseEntity.ok(wordWithAttemptsAndSuccesses);
    }

}
