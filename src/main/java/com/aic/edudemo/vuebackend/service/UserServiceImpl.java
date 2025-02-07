package com.aic.edudemo.vuebackend.service;


import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.entity.User;
import com.aic.edudemo.vuebackend.repository.UserRepository;
import com.aic.edudemo.vuebackend.utils.mapstruct.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public List<User> findAllUser() {

        return userRepository.findAll();

    }

    @Override
    public void registerUser(UserDto user) {
        String encryptedPwd = encryptPassword(user.getUserPwd());
        user.setUserPwdHash(encryptedPwd);
        User userEntity = userMapper.toEntity(user);
        try {
            userRepository.save(userEntity);
            log.info("新增資料: {}", userEntity);
        } catch (Exception e) {
            log.info("新增失敗:" + e.getMessage());
        }
    }

    @Override
    public void updateUser(UserDto user) {
            userRepository.save(userMapper.toEntity(user));


    }

    @Override
    public void deleteUser(Integer userId) {
        System.out.println("有進來嗎");
        userRepository.deleteById(userId);
    }
}
