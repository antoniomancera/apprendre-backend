package com.antonio.apprendrebackend.service;

import com.antonio.apprendrebackend.model.MotPalabra;

import java.util.Optional;

public interface MotPalabraService {
    Optional<MotPalabra> selectRandomMotPalabraPhrase();
}