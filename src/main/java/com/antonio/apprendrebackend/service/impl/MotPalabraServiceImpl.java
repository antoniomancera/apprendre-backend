package com.antonio.apprendrebackend.service.impl;


import com.antonio.apprendrebackend.model.MotPalabra;
import com.antonio.apprendrebackend.model.MotPalabraImportance;
import com.antonio.apprendrebackend.model.MotPalabraPhrase;
import com.antonio.apprendrebackend.repository.MotPalabraImportanceRepository;
import com.antonio.apprendrebackend.service.MotPalabraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MotPalabraServiceImpl implements MotPalabraService {

    @Autowired
    private MotPalabraImportanceRepository motPalabraImportanceRepository;

    @Override
    public MotPalabraPhrase selectRandomMotPalabraPhrase() {
        List<MotPalabraImportance> motPalabraImportances = motPalabraImportanceRepository.findAll();
        List<MotPalabra> weightedList = new ArrayList<>();

        for (MotPalabraImportance mpi : motPalabraImportances) {
            for (int i = 0; i < mpi.getImportanceIndex(); i++) {
                weightedList.add(mpi.getMotPalabra());
            }
        }

        Random random = new Random();
        int randomIndex = random.nextInt(weightedList.size());

        MotPalabra selectedMotPalabra = weightedList.get(randomIndex);
        List<MotPalabraPhrase> phrases = selectedMotPalabra.getPhrases();

        return phrases.get(random.nextInt(phrases.size()));
    }
}