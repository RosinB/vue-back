package com.aic.edudemo.vuebackend.utils.mapstruct;

import com.aic.edudemo.vuebackend.domain.dto.EventAddDto;
import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.entity.Event;
import com.aic.edudemo.vuebackend.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

//    EventAddDto toDto(User entity);
    Event toEntity(EventAddDto dto);
}
