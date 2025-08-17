package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.PartSpeechFoundException;
import com.antonio.apprendrebackend.service.model.PartSpeech;
import com.antonio.apprendrebackend.service.repository.PartSpeechRepository;
import com.antonio.apprendrebackend.service.service.PartSpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartSpeechServiceImpl implements PartSpeechService {
    @Autowired
    private PartSpeechRepository partSpeechRepository;

    /**
     * Given a typeEnum return the complete type
     *
     * @param partSpeechEnum
     * @return
     */
    @Override
    public PartSpeech getByPartSpeech(PartSpeech.PartSpeechEnum partSpeechEnum) {
        Optional<PartSpeech> typeOptional = partSpeechRepository.findByCode(partSpeechEnum);
        if (typeOptional.isEmpty()) {
            throw new PartSpeechFoundException(String.format("Not found any type %s", partSpeechEnum.toString()));
        }
        return typeOptional.get();
    }

    /**
     * Returns all the types available in the database
     *
     * @return List<Type>
     */
    @Override
    public List<PartSpeech> getAllPartSpeechs() {
        return partSpeechRepository.findAll();
    }
}
