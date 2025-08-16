package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.repository.TypeRepository;
import com.antonio.apprendrebackend.service.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    /**
     * Given a typeEnum return the complete type
     *
     * @param typeEnum
     * @return
     */
    @Override
    public Type getByType(Type.TypeEnum typeEnum) {
        Optional<Type> typeOptional = typeRepository.findByCode(typeEnum);
        if (typeOptional.isEmpty()) {
            throw new TypeNotFoundException(String.format("Not found any type %s", typeEnum.toString()));
        }
        return typeOptional.get();
    }

    /**
     * Returns all the types available in the database
     *
     * @return List<Type>
     */
    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }
}
