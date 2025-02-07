package com.aic.edudemo.vuebackend.service;

import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.entity.User;

import java.util.List;

public interface UserService {


        List<User> findAllUser();
        void registerUser(UserDto user);
        void updateUser(UserDto user);
        void deleteUser(Integer userId);
}
