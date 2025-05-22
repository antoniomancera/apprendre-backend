package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.model.Word;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordRepository extends CrudRepository<Word, Integer> {

    List<Word> findByType(Type type);
}
