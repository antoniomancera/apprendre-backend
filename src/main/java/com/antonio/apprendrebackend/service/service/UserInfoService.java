package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;

public interface UserInfoService {
    /**
     * Get the UserInfo for a SupabaseId
     *
     * @param supabaseId
     * @return UserInfo
     * @throws UserInfoNotFoundException if not exist any UserInfo
     */
    UserInfo findBySupabaseId(String supabaseId);

    /**
     * Update an UserInfo with a course
     *
     * @param courseCode
     * @param userInfo
     * @return UserInfo updated
     */
    UserInfo setUserInfoCurrentCourse(String courseCode, UserInfo userInfo);
}
