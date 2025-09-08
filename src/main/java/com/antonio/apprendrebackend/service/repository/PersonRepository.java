package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findAll();
}
