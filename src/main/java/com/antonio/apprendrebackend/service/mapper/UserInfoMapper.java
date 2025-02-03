package com.antonio.apprendrebackend.service.mapper;

import com.antonio.apprendrebackend.service.dto.UserInfoDTO;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "email", source = "email")
    UserInfoDTO toDTO(UserInfo userInfo);
}
