package com.antonio.apprendrebackend.service.config;

import com.antonio.apprendrebackend.service.model.Level;
import com.antonio.apprendrebackend.service.model.PartSpeech;
import com.antonio.apprendrebackend.service.repository.LevelRepository;
import com.antonio.apprendrebackend.service.repository.PartSpeechRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Autowired
    private PartSpeechRepository partSpeechRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (partSpeechRepository.count() == 0) {
                partSpeechRepository.save(new PartSpeech(PartSpeech.PartSpeechEnum.SUSTANTIVE));
                partSpeechRepository.save(new PartSpeech(PartSpeech.PartSpeechEnum.VERB));
                partSpeechRepository.save(new PartSpeech(PartSpeech.PartSpeechEnum.ADJECTIVE));
                partSpeechRepository.save(new PartSpeech(PartSpeech.PartSpeechEnum.ADVERB));
                partSpeechRepository.save(new PartSpeech(PartSpeech.PartSpeechEnum.PRONOUN));
                partSpeechRepository.save(new PartSpeech(PartSpeech.PartSpeechEnum.PREPOSITION));
                partSpeechRepository.save(new PartSpeech(PartSpeech.PartSpeechEnum.CONJUNCTION));
                partSpeechRepository.save(new PartSpeech(PartSpeech.PartSpeechEnum.INTERJECTION));
            }

            if (levelRepository.count() == 0) {
                levelRepository.save(new Level(Level.LevelEnum.A1));
                levelRepository.save(new Level(Level.LevelEnum.A2));
                levelRepository.save(new Level(Level.LevelEnum.B1));
                levelRepository.save(new Level(Level.LevelEnum.B2));
                levelRepository.save(new Level(Level.LevelEnum.C1));
                levelRepository.save(new Level(Level.LevelEnum.C2));
            }
        };
    }
}
