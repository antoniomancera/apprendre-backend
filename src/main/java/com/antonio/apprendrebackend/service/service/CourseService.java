package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.CourseDTO;
import com.antonio.apprendrebackend.service.model.Course;

import java.util.List;

public interface CourseService {
    /**
     * Returns all the courses availables
     *
     * @return List<CourseDTO>
     */
    List<CourseDTO> getAllCourses();

    /**
     * Return a course by their code
     *
     * @param code
     * @return Course
     */
    Course getCourseByCode(String code);
}
