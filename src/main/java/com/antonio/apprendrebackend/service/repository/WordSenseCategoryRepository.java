package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordSenseCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordSenseCategoryRepository extends CrudRepository<WordSenseCategory, Integer> {
    List<WordSenseCategory> findByWordSenseId(Integer wordSensId);
}
