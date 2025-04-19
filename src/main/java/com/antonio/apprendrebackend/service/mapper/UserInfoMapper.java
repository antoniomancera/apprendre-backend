package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.UserInfoDTO;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    @Mapping(target = "supabaseId", source = "supabaseId")
    @Mapping(target = "userName", source = "userName")
    UserInfoDTO toDTO(UserInfo userInfo);
}
