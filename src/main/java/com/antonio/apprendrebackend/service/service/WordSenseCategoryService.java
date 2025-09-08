package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Category;

import java.util.List;

public interface WordSenseCategoryService {
    /**
     * Get all the categories of a wordSense
     *
     * @param wordSenseId
     * @return List<Category>
     */
    List<Category> getCategoryByWordSenseId(Integer wordSenseId);
}
