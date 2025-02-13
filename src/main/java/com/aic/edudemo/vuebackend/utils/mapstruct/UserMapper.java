package com.aic.edudemo.vuebackend.utils.mapstruct;

import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(Users entity);
    Users toEntity(UserDto dto);
}