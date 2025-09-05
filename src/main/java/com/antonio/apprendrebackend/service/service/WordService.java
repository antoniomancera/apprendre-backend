package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.*;
import com.antonio.apprendrebackend.service.exception.PartSpeechFoundException;
import com.antonio.apprendrebackend.service.exception.WordNotFoundException;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.Word;

import java.util.List;

public interface WordService {
    /**
     * Get a word by their Id if exists
     *
     * @param wordId
     * @return
     * @throws WordNotFoundException
     */
    Word getWordById(Integer wordId);

    /**
     * Get all the words that are verbs
     *
     * @return List<WordDTO>
     * @throws PartSpeechFoundException if not exist Verb as a part of speech
     * @throws WordNotFoundException    if not exist any Verb
     */
    List<WordDTO> getAllVerbs();

    /**
     * Return a page with wordWitSenses, that is a collection of word with their respective wordSense
     *
     * @param pageNumber
     * @param pageSize
     * @return a List<WordWithSenseDTO>
     */
    // List<WordWithSenseDTO> getWordWithSensePaginated(Integer pageNumber, Integer pageSize);

    /**
     * Get a list with all the parameters availables to filter for word and wordSense, that are;
     * for all level, category, part of speech; for part of speech variables  person, gender, number;
     * and finally for part of speech with conjugation mood and tense
     *
     * @return WordFilterOptionsDTO
     */
    WordFilterOptionsDTO getAllWordFilterOptions();

    /**
     * Returns a page of WordWithAttemptsAndSuccess applying filter if exists of a language, that is a list of Words with their number
     * of attempts and accuracy
     *
     * @param pageNumber
     * @param pageSize
     * @param wordFilterRequest
     * @param code
     * @return List<WordWithAttemptsAndSuccessDTO>
     */
    List<WordWithAttemptsAndSuccessDTO> getWordWithSensePaginatedByLanguageCodeAplyingWordFilter(Integer pageNumber, Integer pageSize, WordFilterRequestDTO wordFilterRequest, Integer userId, Language.LanguageEnum code);

    /**
     * Returns a page of WordWithAttemptsAndSuccess of a language, that is a list of Words with their number
     * of attempts and accuracy
     *
     * @param pageNumber
     * @param pageSize
     * @param code
     * @return List<WordWithAttemptsAndSuccessDTO>
     */
    List<WordWithAttemptsAndSuccessDTO> getWordWithAttemptsAndSuccessPaginatedByLanguageCode(Integer pageNumber, Integer pageSize, Integer userId, Language.LanguageEnum code);

    /**
     * Get the senses with Info of a word
     *
     * @param wordId
     * @return List<WordSenseInfoWithoutWordDTO>
     */
    List<WordSenseInfoWithoutWordDTO> getWordSenseInfosWithoutWordByWordId(Integer wordId, Integer userId);

    /**
     * Get the senses with Info of a word on applying filters if exists
     *
     * @param wordId
     * @param wordSenseFilterRequest
     * @return List<WordSenseInfoWithoutWordDTO>
     */
    List<WordSenseInfoWithoutWordDTO> getWordSenseInfosWithoutWordByWordIdAplyingWordSenseFilters(Integer wordId, WordSenseFilterRequestDTO wordSenseFilterRequest, Integer userId);
}