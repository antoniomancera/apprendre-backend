package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.Course;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.UserInfoRepository;
import com.antonio.apprendrebackend.service.service.CourseService;
import com.antonio.apprendrebackend.service.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private CourseService courseService;


    /**
     * Get the UserInfo for a SupabaseId
     *
     * @param supabaseId
     * @return UserInfo
     * @throws UserInfoNotFoundException if not exist any UserInfo
     */
    @Override
    public UserInfo findBySupabaseId(String supabaseId) {
        logger.debug("Called findBySupabaseId in UserInfoService for supabase-{}", supabaseId);

        return userInfoRepository.findBySupabaseId(supabaseId)
                .orElseThrow(() -> new UserInfoNotFoundException("Not found userInfo"));
    }

    /**
     * Update an UserInfo with a course
     *
     * @param courseCode
     * @param userInfo
     * @return UserInfo updated
     */
    @Override
    public UserInfo setUserInfoCurrentCourse(String courseCode, UserInfo userInfo) {
        logger.debug("Called setUserInfoCurrentCourse in UserInfoService for course-{}, and user-{}", courseCode, userInfo);

        Course currentCourse = courseService.getCourseByCode(courseCode);
        userInfo.setCurrentCourse(currentCourse);
        return userInfoRepository.save(userInfo);
    }

}
