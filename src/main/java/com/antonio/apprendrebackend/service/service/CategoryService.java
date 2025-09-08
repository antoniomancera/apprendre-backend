package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Category;

import java.util.List;

public interface CategoryService {
    /**
     * Return all the categories availables in the database
     *
     * @return List<Category>
     */
    List<Category> getAllCategories();
}
