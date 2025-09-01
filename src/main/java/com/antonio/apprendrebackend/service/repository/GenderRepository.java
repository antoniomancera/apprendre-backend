package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Gender;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenderRepository extends CrudRepository<Gender, Integer> {
    List<Gender> findAll();
}
