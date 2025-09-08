package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Person;

import java.util.List;

public interface PersonService {
    /**
     * Return the persons(first, second...) availables in the database
     *
     * @return List<Person>
     */
    List<Person> getAllPersons();
}
