package com.aic.edudemo.vuebackend.service;


import com.aic.edudemo.vuebackend.domain.dto.UserAdvancedDto;
import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.dto.UserManageDto;
import com.aic.edudemo.vuebackend.domain.entity.Manage;
import com.aic.edudemo.vuebackend.domain.entity.UserHistory;
import com.aic.edudemo.vuebackend.domain.entity.UserRole;
import com.aic.edudemo.vuebackend.domain.entity.Users;
import com.aic.edudemo.vuebackend.repository.ManageRepository;
import com.aic.edudemo.vuebackend.repository.UserHistoryRepository;
import com.aic.edudemo.vuebackend.repository.UserRepository;
import com.aic.edudemo.vuebackend.repository.UserRoleRepository;
import com.aic.edudemo.vuebackend.utils.mapstruct.UserHistoryMapper;
import com.aic.edudemo.vuebackend.utils.mapstruct.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class UserService {
    private static final String UPLOAD_DIR = "C:\\Users\\Rosin Huang\\Desktop\\vue-front\\client\\public\\img\\";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ManageRepository manageRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserMapper userMapper;
    private final UserHistoryMapper userHistoryMapper;
    private final UserHistoryRepository userHistoryRepository;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRoleRepository userRoleRepository, RedisTemplate<String, Object> redisTemplate, UserRepository userRepository, UserMapper userMapper, ManageRepository manageRepository, UserHistoryMapper userHistoryMapper, UserHistoryRepository userHistoryRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.manageRepository = manageRepository;
        this.userHistoryMapper = userHistoryMapper;
        this.userHistoryRepository = userHistoryRepository;
        this.redisTemplate = redisTemplate;
        this.userRoleRepository = userRoleRepository;
    }

    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public List<Users> findAllUser() {

        String key = "userAll:";
        List<Users> users = (List<Users>) redisTemplate.opsForValue().get(key);
        if (users == null) {
            users = userRepository.findAll();
            redisTemplate.opsForValue().set(key, users, 10, TimeUnit.MINUTES);
        }


        return users;
    }

    @Transactional
    public void registerUser(UserDto user) {
        System.out.println("註冊的密碼是:" + user.getUserPwd());
        String encryptedPwd = encryptPassword(user.getUserPwd());
        user.setUserPwdHash(encryptedPwd);
        Users userEntity = userMapper.toEntity(user);
        Users userData = userRepository.save(userEntity);
        Manage manage = Manage.builder().
                userId(userData.getUserId()).
                userBio(" ").
                userImgPath(" ").
                build();
        manageRepository.save(manage);

        Map<String, Integer> roleMapping = Map.of(
                "管理員", 1,
                "員工", 2,
                "會員", 3
        );
        Integer roleId = roleMapping.get(user.getUserType());
        UserRole role = UserRole.builder()
                .userId(userData.getUserId())
                .roleId(roleId)
                .build();
        userRoleRepository.save(role);

    }

    public void updateUser(UserDto user) {
        userRepository.save(userMapper.toEntity(user));
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public List<Users> searchUser(String type, String keyword) {
        return switch (type) {
            case "all" -> userRepository.findUserByAllLike(keyword);
            case "userName" -> userRepository.findByUserNameContaining(keyword);
            case "userEmail" -> userRepository.findByUserEmailContaining(keyword);
            case "userPhone" -> userRepository.findByUserPhoneContaining(keyword);
            case "userIdCard" -> userRepository.findByUserIdCardContaining(keyword);
            default -> throw new RuntimeException("缺少type");
        };
    }

    public List<Users> searchUserByBirthdate(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        return userRepository.findByUserBirthDateBetween(fromDate, toDate);
    }

    public List<Users> searchUserAdvanced(UserAdvancedDto dto) {

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
    public void batchAddUser(List<Users> users) {
        users.forEach(user -> {
            user.setUserPwdHash(encryptPassword(user.getUserPwdHash()));
        });
        List<Users> userList = userRepository.saveAll(users);
        List<Manage> manageList = userList.stream().map(
                user -> Manage.builder().
                        userId(user.getUserId()).
                        userBio(" ").
                        userImgPath(" ").
                        build()).toList();
        manageRepository.saveAll(manageList);
    }

    public UserManageDto getUserManageDto(String userName) {
        Integer userId = userRepository.findUserIdByUserName(userName);
        return userRepository.findUserManageDto(userId);
    }

    public void updateUserAvatar(Integer userId, MultipartFile file) {
        String filePath = saveImagePath(file);
        Manage manageData = manageRepository.findByUserId(userId);

        Manage manage = Manage.builder().
                userId(userId).
                manageId(manageData.getManageId()).
                userBio(manageData.getUserBio()).
                manageUpdatedAt(manageData.getManageUpdatedAt()).
                userImgPath(filePath).
                build();
        manageRepository.save(manage);
    }

    public String saveImagePath(MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            file.transferTo(new File(filePath));
            return "img/" + fileName;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    public UserManageDto updateUserManageDto(UserManageDto userManageDto) {
        UserHistory his = userHistoryMapper.toEntity(userManageDto);
        userHistoryRepository.save(his);
        Users userData = userRepository.findById(userManageDto.getUserId()).get();
        Users user = Users.builder().
                userName(userManageDto.getUserName()).
                userEmail(userManageDto.getUserEmail()).
                userPhone(userManageDto.getUserPhone()).
                userId(userManageDto.getUserId()).
                userBirthDate(userManageDto.getUserBirthDate()).
                userIsVerified(userManageDto.getUserIsVerified()).
                userPwdHash(userData.getUserPwdHash()).
                userIdCard(userManageDto.getUserIdCard()).
                userRegdate(userData.getUserRegdate()).
                build();
        userRepository.save(user);
        Manage manageData = manageRepository.findByUserId(user.getUserId());

        Manage manage = Manage.builder().
                manageId(manageData.getManageId()).
                userId(manageData.getUserId()).
                userBio(userManageDto.getUserBio()).
                manageUpdatedAt(manageData.getManageUpdatedAt()).
                userImgPath(manageData.getUserImgPath()).
                build();
        manageRepository.save(manage);
        return userManageDto;

    }

    public Page<UserHistory> getUserHistoryList(Integer userId, Pageable pageable) {

        return userHistoryRepository.findByUserIdOrderByCreateAtDesc(userId, pageable);

    }


}
