package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.UserInfoNotFoundException;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.UserInfoRepository;
import com.antonio.apprendrebackend.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public UserInfo getUserInfo() {
        return userInfoRepository.findFirstByOrderByDateAsc();
    }

    /**
     * Found if exists the user by email
     *
     * @param email
     * @return UserInfo
     * @throws UserInfoNotFoundException
     */
    @Override
    public UserInfo getByEmail(String email) throws UserInfoNotFoundException {
        return userInfoRepository.findByEmail(email)
                .orElseThrow(() -> new UserInfoNotFoundException("Not found userInfo with email: " + email));
    }


    /**
     * Get the UserInfo given the SupabaseId
     *
     * @param supabaseId
     * @return UserInfo
     * @throws UserInfoNotFoundException if not exist any UserInfo
     */
    @Override
    public UserInfo findBySupabaseId(String supabaseId) {
        return userInfoRepository.findBySupabaseId(supabaseId)
                .orElseThrow(() -> new UserInfoNotFoundException("Not found userInfo"));
    }

}
