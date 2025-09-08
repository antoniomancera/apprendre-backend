package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Category;
import com.antonio.apprendrebackend.service.repository.CategoryRepository;
import com.antonio.apprendrebackend.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Return all the categories availables in the database
     *
     * @return List<Category>
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
