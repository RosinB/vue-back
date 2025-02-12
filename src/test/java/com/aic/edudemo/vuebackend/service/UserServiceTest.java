package com.aic.edudemo.vuebackend.service;

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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // ✅ 只用 Mockito，不載入 Spring Boot
public class UserServiceTest {

    @InjectMocks  // ✅ 讓 Mockito 幫你注入 @Mock 物件
    private UserService userService;

    @Mock  // ✅ 避免連接真正的資料庫
    private UserRepository userRepository;

    @Mock
    private ManageRepository manageRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisTemplate<String,Object> redisTemplate;

    @Mock
    ValueOperations<String,Object> valueOperations;

    @Mock
    private MultipartFile mockFile;

    private User user;
    private Manage manage;

    @BeforeEach
    void setUp() {
        user = new User(1, "ruka", "hashed_password", "094551234", "ruka@example.com", "A123456789", null, null, false);
        manage = new Manage(1, user.getUserId(), "img_path", "bio", null);

    }

    @Test
    void testDeleteUser() {
        Integer userId = 1;
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }




    @Test
    void testBatchAddUser() {
        List<User> users = Arrays.asList(user, user);
        when(userRepository.saveAll(anyList())).thenReturn(users);

        userService.batchAddUser(users);

        verify(userRepository, times(1)).saveAll(anyList());
        verify(manageRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testUpdateUserAvatar() {
        Integer userId = 1;
        when(manageRepository.findByUserId(userId)).thenReturn(manage);
        when(mockFile.getOriginalFilename()).thenReturn("test.jpg");

        userService.updateUserAvatar(userId, mockFile);

        verify(manageRepository, times(1)).save(any(Manage.class));
    }

    @Test
    void testSearchUser() {
        when(userRepository.findByUserNameContaining("ruka")).thenReturn(List.of(user));

        List<User> result = userService.searchUser("userName", "ruka");

        assertEquals(1, result.size());
        assertEquals("ruka", result.get(0).getUserName());

        // ✅ 測試搜尋 Email
        when(userRepository.findByUserEmailContaining("ruka@example.com")).thenReturn(List.of(user));
        List<User> emailResult = userService.searchUser("userEmail", "ruka@example.com");
        assertEquals(1, emailResult.size());
        assertEquals("ruka@example.com", emailResult.get(0).getUserEmail());

        // ✅ 測試搜尋手機號碼
        when(userRepository.findByUserPhoneContaining("094551234")).thenReturn(List.of(user));
        List<User> phoneResult = userService.searchUser("userPhone", "094551234");
        assertEquals(1, phoneResult.size());
        assertEquals("094551234", phoneResult.get(0).getUserPhone());
    }
}
