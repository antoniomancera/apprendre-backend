package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.MotPalabraPool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MotPalabraPoolRepository extends CrudRepository<MotPalabraPool, Integer> {
    @Query(value = "SELECT * FROM MotPalabraPool ORDER BY RAND() LIMIT 1", nativeQuery = true)
    MotPalabraPool findRandomMotPalabraPool();
}
