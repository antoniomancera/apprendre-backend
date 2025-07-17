package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Success;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SuccessRepository extends CrudRepository<Success, Integer> {
    Optional<Success> findBySuccessEnum(Success.SuccessEnum successEnum);
}
