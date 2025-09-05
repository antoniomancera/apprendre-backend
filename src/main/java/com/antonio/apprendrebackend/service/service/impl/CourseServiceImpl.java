package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.CourseDTO;
import com.antonio.apprendrebackend.service.exception.CourseNotFoundException;
import com.antonio.apprendrebackend.service.mapper.CourseMapper;
import com.antonio.apprendrebackend.service.model.Course;
import com.antonio.apprendrebackend.service.repository.CourseRepository;
import com.antonio.apprendrebackend.service.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    /**
     * Returns all the courses availables
     *
     * @return List<CourseDTO>
     */
    @Override
    public List<CourseDTO> getAllCourses() {
        logger.debug("Called getAllCourses in CourseService");

        Iterable<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : courses) {
            courseDTOS.add(courseMapper.toDTO(course));
        }

        return courseDTOS;
    }

    /**
     * Return a course by their code
     *
     * @param code
     * @return Course
     */
    @Override
    public Course getCourseByCode(String code) {
        logger.debug("Called getCourseByCode in CourseService by code-{}", code);

        return courseRepository.findByCode(code).orElseThrow(() -> new CourseNotFoundException(String.format("Course-%s not found", code)));
    }
}
