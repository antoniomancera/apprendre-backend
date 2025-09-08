package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Number;
import com.antonio.apprendrebackend.service.repository.NumberRepository;
import com.antonio.apprendrebackend.service.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberServiceImpl implements NumberService {
    @Autowired
    private NumberRepository numberRepository;

    /**
     * Returns all the numbers(singular, plural) available in the databases
     *
     * @return List<Number>
     */
    @Override
    public List<Number> getAllNumbers() {
        return numberRepository.findAll();
    }
}
