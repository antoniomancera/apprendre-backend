package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.PhraseTranslationDTO;
import com.antonio.apprendrebackend.service.dto.PhraseTranslationWithWordTranslationsDTO;
import com.antonio.apprendrebackend.service.exception.PhraseNotFoundException;
import com.antonio.apprendrebackend.service.model.Type;

import java.util.List;

public interface TypeService {
    /**
     * Given a typeEnum return the complete type
     *
     * @param typeEnum
     * @return
     */
    Type getByType(Type.TypeEnum typeEnum);
}
