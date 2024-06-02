package com.antonio.apprendrebackend.service.impl;


import com.antonio.apprendrebackend.model.MotPalabra;
import com.antonio.apprendrebackend.model.MotPalabraPool;
import com.antonio.apprendrebackend.repository.MotPalabraPoolRepository;
import com.antonio.apprendrebackend.repository.MotPalabraRepository;
import com.antonio.apprendrebackend.service.MotPalabraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MotPalabraServiceImpl implements MotPalabraService {
    @Autowired
    MotPalabraPoolRepository motPalabraPoolRepository;
    @Autowired
    MotPalabraRepository motPalabraRepository;

    @Override
    public Optional<MotPalabra> selectRandomMotPalabraPhrase() {
        MotPalabraPool motPalabraPool = motPalabraPoolRepository.findRandomMotPalabraPool();

        Optional<MotPalabra> motPalabra = motPalabraRepository.findById(motPalabraPool.getMotPalabra().getMot().getId());

        return motPalabra;
    }
}