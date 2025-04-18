package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
    UserInfo findFirstByOrderByDateAsc();

    Optional<UserInfo> findBySupabaseId(String supabaseId);

    Optional<UserInfo> findByEmail(String email);
}
