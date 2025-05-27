package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.WordCollectionItemNotFoundException;
import com.antonio.apprendrebackend.service.model.WordCollection;
import com.antonio.apprendrebackend.service.model.WordCollectionItem;
import com.antonio.apprendrebackend.service.repository.WordCollectionItemRepository;
import com.antonio.apprendrebackend.service.service.WordCollectionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordCollectionItemServiceImpl implements WordCollectionItemService {
    @Autowired
    private WordCollectionItemRepository wordCollectionItemRepository;

    /**
     * Return all the WordCollectionItem of a WordCollection
     *
     * @param wordCollectionEnum
     * @return List<WordCollectionItem>
     * @throws WordCollectionItemNotFoundException if any WordCollectionItem related to the wordCollectionEnum is found
     */
    @Override
    public List<WordCollectionItem> getWordCollectionItemsByWordCollectionEnum(WordCollection.WordCollectionEnum wordCollectionEnum) {
        List<WordCollectionItem> wordCollectionItems = wordCollectionItemRepository.findByWordCollection_WordCollectionEnum(wordCollectionEnum);
        if (wordCollectionItems.isEmpty()) {
            throw new WordCollectionItemNotFoundException(String.format("Not found any wordCollectionItem for wordCollection %s", wordCollectionEnum));
        }
        return wordCollectionItems;
    }
}
