package com.aic.edudemo.vuebackend.service;


import com.aic.edudemo.vuebackend.domain.dto.UserAdvancedDto;
import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.dto.UserManageDto;
import com.aic.edudemo.vuebackend.domain.entity.Manage;
import com.aic.edudemo.vuebackend.domain.entity.User;
import com.aic.edudemo.vuebackend.repository.ManageRepository;
import com.aic.edudemo.vuebackend.repository.UserRepository;
import com.aic.edudemo.vuebackend.utils.mapstruct.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class UserService {

    private final ManageRepository manageRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, ManageRepository manageRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.manageRepository = manageRepository;
    }

    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public void registerUser(UserDto user) {
        String encryptedPwd = encryptPassword(user.getUserPwd());
        user.setUserPwdHash(encryptedPwd);
        User userEntity = userMapper.toEntity(user);




        User userData =  userRepository.save(userEntity);

        Manage manage = Manage.builder().
                userId(userData.getUserId()).
                userBio(" ").
                userImgPath(" ").
                build();
        manageRepository.save(manage);



        log.info("新增資料1號: {},新增資料2號{}", userEntity, manage);
    }

    public void updateUser(UserDto user) {
        userRepository.save(userMapper.toEntity(user));
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public List<User> searchUser(String type, String keyword) {
        return switch (type) {
            case "all" -> userRepository.findUserByAllLike(keyword);
            case "userName" -> userRepository.findByUserNameContaining(keyword);
            case "userEmail" -> userRepository.findByUserEmailContaining(keyword);
            case "userPhone" -> userRepository.findByUserPhoneContaining(keyword);
            case "userIdCard" -> userRepository.findByUserIdCardContaining(keyword);
            default -> throw new RuntimeException("缺少type");
        };

    }

    public List<User> searchUserByBirthdate(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);

        return userRepository.findByUserBirthDateBetween(fromDate, toDate);
    }

    public List<User> searchUserAdvanced(UserAdvancedDto dto) {

        return userRepository.findByAdvancedSearch(
                dto.getUserName(),
                dto.getUserPhone(),
                dto.getUserEmail(),
                dto.getUserIdCard(),
                dto.getUserIsVerified(),
                dto.getUserBirthDateStart(),
                dto.getUserBirthDateEnd()
        );

    }

    @Transactional
    public void batchAddUser(List<User> users) {
        users.forEach(user -> {
            user.setUserPwdHash(encryptPassword(user.getUserPwdHash()));
        });

        List<User> userList=userRepository.saveAll(users);

        List<Manage> manageList=userList.stream().map(
                user -> Manage.builder().
                    userId(user.getUserId()).
                    userBio(" ").
                    userImgPath(" ").
                    build()).toList();
        manageRepository.saveAll(manageList);
    }


    public List<UserManageDto> getUserManageDto(String userName) {
        Integer userId= userRepository.findUserIdByUserName(userName);

        return null;


    }












}
