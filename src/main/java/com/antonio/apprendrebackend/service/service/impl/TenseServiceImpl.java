package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.MoodWithTenseDTO;
import com.antonio.apprendrebackend.service.dto.TenseWithoutMoodDTO;
import com.antonio.apprendrebackend.service.exception.TenseNotFoundException;
import com.antonio.apprendrebackend.service.mapper.MoodMapper;
import com.antonio.apprendrebackend.service.mapper.TenseWithoutMoodMapper;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.Mood;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.repository.TenseRepository;
import com.antonio.apprendrebackend.service.service.TenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TenseServiceImpl implements TenseService {
    @Autowired
    private TenseRepository tenseRepository;

    @Autowired
    private TenseWithoutMoodMapper tenseWithoutMoodMapper;
    @Autowired
    private MoodMapper moodMapper;

    /**
     * Returns a list of tenses given a language
     *
     * @param language
     * @return List<Tense>
     * @throws TenseNotFoundException if any tense related to the language is found
     */
    @Override
    public List<Tense> getByLanguage(Language language) {
        List<Tense> tenses = tenseRepository.findByLanguage(language);
        if (tenses.isEmpty()) {
            throw new TenseNotFoundException(String.format("Not found any tense for the language %s", language.getName()));
        }
        return tenses;
    }

    /**
     * Returns all the tenses in the database
     *
     * @return List<Tense>
     */
    @Override
    public List<Tense> getAllTenses() {
        return tenseRepository.findAll();
    }

    /**
     * Returns all the moods with their tenses, all the tenses without a mood are included
     * in a false mood with the id -1. Furthermore, the tenses did not contains their mood, in
     * order to avoid repeat information
     *
     * @return List<MoodWithTenseDTO>
     */
    @Override
    public List<MoodWithTenseDTO> getAllMoodWithTense() {
        List<Tense> tenses = getAllTenses();
        Mood nullMood = new Mood(-1, "NO_MOOD");

        Map<Mood, List<Tense>> groupedByMood = tenses.stream()
                .collect(Collectors.groupingBy(
                        tense -> tense.getMood() != null ? tense.getMood() : nullMood,
                        Collectors.toList()
                ));

        return groupedByMood.entrySet().stream().map(moodWithTenses -> new MoodWithTenseDTO(moodMapper.toDTO(moodWithTenses.getKey()), moodWithTenses.getValue().stream().map(tenseWithoutMoodMapper::toDTO).collect(Collectors.toList()))).collect(Collectors.toList());
    }
}
