package com.antonio.apprendrebackend.config;

import com.antonio.apprendrebackend.model.Type;
import com.antonio.apprendrebackend.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Autowired
    private TypeRepository typeRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (typeRepository.count() == 0) {
                typeRepository.save(new Type("Sustantivo", Type.TypeEnum.SUSTANTIVE));
                typeRepository.save(new Type("Verbo", Type.TypeEnum.VERB));
                typeRepository.save(new Type("Adjetivo", Type.TypeEnum.ADJECTIVE));
                typeRepository.save(new Type("Adverbio", Type.TypeEnum.ADVERB));
                typeRepository.save(new Type("Pronombre", Type.TypeEnum.PRONOUN));
                typeRepository.save(new Type("Preposición", Type.TypeEnum.PREPOSITION));
                typeRepository.save(new Type("Conjunción", Type.TypeEnum.CONJUNCTION));
                typeRepository.save(new Type("Interjección", Type.TypeEnum.INTERJECTION));
            }
        };
    }
}
