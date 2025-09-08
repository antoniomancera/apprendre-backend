package com.antonio.apprendrebackend.service.repository;


import com.antonio.apprendrebackend.service.model.PartSpeech;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface PartSpeechRepository extends CrudRepository<PartSpeech, Integer> {
    List<PartSpeech> findAll();

    Optional<PartSpeech> findByCode(PartSpeech.PartSpeechEnum code);
}