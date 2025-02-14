package com.aic.edudemo.vuebackend.service;

import com.aic.edudemo.vuebackend.repository.UserRepository;
import com.aic.edudemo.vuebackend.repository.UserRoleRepository;
import com.aic.edudemo.vuebackend.utils.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoginService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    public LoginService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public boolean verifyPassword(String rawPassword, String storedPassword) {
        return passwordEncoder.matches(rawPassword, storedPassword);
    }
    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }




    public String loginUser(String username, String password) {

        String hashedPasswordReal = userRepository.findUserPwdHashByUserName(username);
        Integer userId=userRepository.findUserIdByUserName(username);

        if(passwordEncoder.matches(password, hashedPasswordReal))
        {
            List<String> roles = userRoleRepository.findRoleNameByUserId(userId);

            String token = JwtUtil.generateToken(username,userId,roles);

            return token;
        }
        log.info("認證失敗-> userName:{},password:{}",username,password);
        throw new RuntimeException("登入失敗");


    }




}
