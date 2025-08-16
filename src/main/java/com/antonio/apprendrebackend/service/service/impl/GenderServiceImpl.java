package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Gender;
import com.antonio.apprendrebackend.service.repository.GenderRepository;
import com.antonio.apprendrebackend.service.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {
    @Autowired
    private GenderRepository genderRepository;

    /**
     * Returns all the genders availables in the database
     *
     * @return List<Gender>
     */
    @Override
    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }
}
