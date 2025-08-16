package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();
}
