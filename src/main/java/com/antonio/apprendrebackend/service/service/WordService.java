package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.dto.WordFilterOptionsDTO;
import com.antonio.apprendrebackend.service.dto.WordWithSenseDTO;
import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordNotFoundException;
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
     * @throws TypeNotFoundException if not exist Verb as a type
     * @throws WordNotFoundException if not exist any Verb
     */
    List<WordDTO> getAllVerbs();

    /**
     * Return a page with wordWitSenses, that is a collection of word with their respective wordSense
     *
     * @param pageNumber
     * @param pageSize
     * @return a List<WordWithSenseDTO>
     */
    List<WordWithSenseDTO> getWordWithSensePaginated(Integer pageNumber, Integer pageSize);

    /**
     * Get a list with all the parameters availables to filter for word and wordSense, that are;
     * for all level, category, part of speech; for part of speech variables  person, gender, number;
     * and finally for part of speech with conjugation mood and tense
     *
     * @return WordFilterOptionsDTO
     */
    WordFilterOptionsDTO getAllWordFilterOptions();

    List<WordWithSenseDTO> getWordWithSensePaginatedAplyingWordSenseFilter(Integer pageNumber, Integer pageSize, WordFilterOptionsDTO wordFilterOptionsDTO);
}