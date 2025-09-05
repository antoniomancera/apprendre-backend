package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    Optional<Course> findByCode(String code);
}
