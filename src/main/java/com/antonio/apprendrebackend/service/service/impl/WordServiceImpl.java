package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.exception.WordNotFoundException;
import com.antonio.apprendrebackend.service.mapper.WordMapper;
import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.Word;
import com.antonio.apprendrebackend.service.repository.WordRepository;
import com.antonio.apprendrebackend.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WordServiceImpl implements WordService {
    @Autowired
    WordRepository wordRepository;
    @Autowired
    TypeService typeService;
    @Autowired
    WordMapper wordMapper;

    @Override
    public Word getById(Integer wordId) {
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
        Type type = typeService.getByType(Type.TypeEnum.VERB);
        List<Word> verbs = wordRepository.findByType(type);
        if (verbs.isEmpty()) {
            throw new WordNotFoundException("Not found any verb");
        }
        return verbs.stream()
                .map(wordMapper::toDTO)
                .collect(Collectors.toList());
    }
}