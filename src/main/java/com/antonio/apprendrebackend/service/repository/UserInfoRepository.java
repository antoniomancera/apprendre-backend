package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
    UserInfo findFirstByOrderByDateAsc();
}
