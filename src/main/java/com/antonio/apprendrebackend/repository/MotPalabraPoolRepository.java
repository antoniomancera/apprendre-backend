package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.MotPalabraPool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MotPalabraPoolRepository extends CrudRepository<MotPalabraPool, Integer> {
    @Query(value = "SELECT * FROM mot_palabra_pool ORDER BY RAND() LIMIT 1", nativeQuery = true)
    MotPalabraPool findRandomMotPalabraPool();
}
