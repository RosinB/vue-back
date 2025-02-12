package com.aic.edudemo.vuebackend.utils.mapstruct;

import com.aic.edudemo.vuebackend.domain.dto.UserManageDto;
import com.aic.edudemo.vuebackend.domain.entity.UserHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserHistoryMapper {

    @Mapping(source = "userId", target = "userId") // ✅ 確保 userId 映射
    UserHistory toEntity(UserManageDto dto);
}
