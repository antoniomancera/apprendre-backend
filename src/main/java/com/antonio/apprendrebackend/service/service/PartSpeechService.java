package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.PartSpeech;

import java.util.List;

public interface PartSpeechService {
    /**
     * Given a partSpeechEnum return the complete PartSpeech
     *
     * @param partSpeechEnum
     * @return PartSpeech
     */
    PartSpeech getByPartSpeech(PartSpeech.PartSpeechEnum partSpeechEnum);

    /**
     * Returns all the part of speech available in the database
     *
     * @return List<PartSpeech>
     */
    List<PartSpeech> getAllPartSpeechs();
}
