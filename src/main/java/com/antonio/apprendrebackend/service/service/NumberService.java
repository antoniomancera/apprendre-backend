package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Number;

import java.util.List;

public interface NumberService {
    /**
     * Returns all the numbers(singular, plural) available in the databases
     *
     * @return List<Number>
     */
    List<Number> getAllNumbers();
}
