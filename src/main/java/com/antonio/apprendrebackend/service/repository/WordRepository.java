package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.PartSpeech;
import com.antonio.apprendrebackend.service.model.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends CrudRepository<Word, Integer>, JpaSpecificationExecutor<Word> {

    List<Word> findByPartSpeech(PartSpeech partSpeech);

    Page<Word> findAll(Pageable pageable);

    Page<Word> findAll(Specification spec, Pageable pageable);
}
