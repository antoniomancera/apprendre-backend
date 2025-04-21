package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;

public interface UserInfoService {
    /**
     * Get the UserInfo given the SupabaseId
     *
     * @param supabaseId
     * @return UserInfo
     * @throws UserInfoNotFoundException if not exist any UserInfo
     */
    UserInfo findBySupabaseId(String supabaseId);
}
