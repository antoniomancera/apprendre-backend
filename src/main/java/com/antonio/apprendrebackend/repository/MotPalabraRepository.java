package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.MotPalabra;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MotPalabraRepository extends CrudRepository<MotPalabra, Integer> {
}
