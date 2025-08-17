package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.PartSpeech;
import com.antonio.apprendrebackend.service.model.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordRepository extends CrudRepository<Word, Integer> {

    List<Word> findByPartSpeech(PartSpeech partSpeech);

    Page<Word> findAll(Pageable pageable);
}
