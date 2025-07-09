package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.exception.WordCollectionItemNotFoundException;
import com.antonio.apprendrebackend.service.model.WordCollection;
import com.antonio.apprendrebackend.service.model.WordCollectionItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordCollectionItemRepository extends CrudRepository<WordCollectionItem, Integer> {
    /**
     * Return all the WordCollectionItem of a WordCollection
     *
     * @param wordCollectionEnum
     * @return List<WordCollectionItem>
     * @throws WordCollectionItemNotFoundException if any WordCollectionItem related to the wordCollectionEnum is found
     */
    List<WordCollectionItem> findByWordCollection_WordCollectionEnum(WordCollection.WordCollectionEnum wordCollectionEnum);
}
