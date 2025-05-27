package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.WordCollectionItemNotFoundException;
import com.antonio.apprendrebackend.service.model.WordCollection;
import com.antonio.apprendrebackend.service.model.WordCollectionItem;

import java.util.List;

public interface WordCollectionItemService {
    /**
     * Return all the WordCollectionItem of a WordCollection
     *
     * @param wordCollectionEnum
     * @return List<WordCollectionItem>
     * @throws WordCollectionItemNotFoundException if any WordCollectionItem related to the wordCollectionEnum is found
     */
    List<WordCollectionItem> getWordCollectionItemsByWordCollectionEnum(WordCollection.WordCollectionEnum wordCollectionEnum);
}
