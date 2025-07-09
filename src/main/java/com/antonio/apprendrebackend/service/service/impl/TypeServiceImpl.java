package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.TypeRepository;
import com.antonio.apprendrebackend.service.repository.UserInfoRepository;
import com.antonio.apprendrebackend.service.service.TypeService;
import com.antonio.apprendrebackend.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeRepository typeRepository;

    /**
     * Given a typeEnum return the complete type
     *
     * @param typeEnum
     * @return
     */
    @Override
    public Type getByType(Type.TypeEnum typeEnum) {
        Optional<Type> typeOptional = typeRepository.findByType(typeEnum);
        if (typeOptional.isEmpty()) {
            throw new TypeNotFoundException(String.format("Not found any type %s", typeEnum.toString()));
        }
        return typeOptional.get();
    }
}
