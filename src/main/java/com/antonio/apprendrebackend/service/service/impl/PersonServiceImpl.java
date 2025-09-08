package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Person;
import com.antonio.apprendrebackend.service.repository.PersonRepository;
import com.antonio.apprendrebackend.service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    /**
     * Return the persons(first, second...) availables in the database
     *
     * @return List<Person>
     */
    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
