package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.ConjugationCompleteDTO;


import java.util.List;

public interface ConjugationVerbService {
    List<ConjugationCompleteDTO> getConjugationComplete(int wordISenseId);
}
