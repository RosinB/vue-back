package com.aic.edudemo.vuebackend.service;

import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.entity.Manage;
import com.aic.edudemo.vuebackend.domain.entity.User;
import com.aic.edudemo.vuebackend.repository.ManageRepository;
import com.aic.edudemo.vuebackend.repository.UserRepository;
import com.aic.edudemo.vuebackend.utils.mapstruct.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//讓 Mockito 幫我們處理 Mock 物件
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks //// 測試的目標
    private UserService userService;

    @Mock    //避免實際連接資料庫
    private UserRepository userRepository;

    @Mock
    private ManageRepository manageRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MultipartFile mockFile;

    private User user;
    private Manage manage;

    @BeforeEach
    void setUp() {
        user = new User(1, "ruka", "hashed_password", "094551234", "ruka@example.com", "A123456789", null, null, false);
        manage = new Manage(1, user.getUserId(), "img_path", "bio", null);
    }

//


    @Test
    void testDeleteUser() {
        // Arrange
        Integer userId = 1;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testFindAllUser() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(user));// 模擬 `findAll()` 回傳一個使用者列表

        // Act
        List<User> result = userService.findAllUser();

        // Assert
        assertEquals(1, result.size());
        assertEquals("ruka", result.get(0).getUserName());
    }

    @Test
    void testBatchAddUser() {
        // Arrange
        List<User> users = Arrays.asList(user, user);
        when(userRepository.saveAll(anyList())).thenReturn(users);

        // Act
        userService.batchAddUser(users);

        // Assert
        verify(userRepository, times(1)).saveAll(users);
        verify(manageRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testUpdateUserAvatar() {
        // Arrange
        Integer userId = 1;
        when(manageRepository.findByUserId(userId)).thenReturn(manage);
        when(mockFile.getOriginalFilename()).thenReturn("test.jpg");

        // Act
        userService.updateUserAvatar(userId, mockFile);

        // Assert
        verify(manageRepository, times(1)).save(any(Manage.class));
    }

    @Test
    void testSearchUser() {
        // Arrange
        when(userRepository.findByUserNameContaining("ruka")).thenReturn(List.of(user));

        // Act
        List<User> result = userService.searchUser("userName", "ruka");

        // Assert
        assertEquals(1, result.size());
        assertEquals("ruka", result.get(0).getUserName());
    }
}
