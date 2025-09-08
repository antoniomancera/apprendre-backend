package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Gender;

import java.util.List;

public interface GenderService {
    /**
     * Returns all the genders availables in the database
     *
     * @return List<Gender>
     */
    List<Gender> getAllGenders();
}
