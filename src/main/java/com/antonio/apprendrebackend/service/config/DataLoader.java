package com.antonio.apprendrebackend.service.config;

import com.antonio.apprendrebackend.service.model.Level;
import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.repository.LevelRepository;
import com.antonio.apprendrebackend.service.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (typeRepository.count() == 0) {
                typeRepository.save(new Type(Type.TypeEnum.SUSTANTIVE));
                typeRepository.save(new Type(Type.TypeEnum.VERB));
                typeRepository.save(new Type(Type.TypeEnum.ADJECTIVE));
                typeRepository.save(new Type(Type.TypeEnum.ADVERB));
                typeRepository.save(new Type(Type.TypeEnum.PRONOUN));
                typeRepository.save(new Type(Type.TypeEnum.PREPOSITION));
                typeRepository.save(new Type(Type.TypeEnum.CONJUNCTION));
                typeRepository.save(new Type(Type.TypeEnum.INTERJECTION));
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
