package com.aic.edudemo.vuebackend.controller;


import com.aic.edudemo.vuebackend.domain.dto.UserAdvancedDto;
import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.dto.UserManageDto;
import com.aic.edudemo.vuebackend.domain.entity.UserHistory;
import com.aic.edudemo.vuebackend.domain.entity.Users;
import com.aic.edudemo.vuebackend.service.LoginService;
import com.aic.edudemo.vuebackend.service.UserService;
import com.aic.edudemo.vuebackend.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "使用者管理", description = "提供使用者 CRUD 相關 API")
public class UserController {

    private final UserService userService;
    private final LoginService loginService;


    @Operation(summary = "取得所有用戶", description = "此 API 回傳所有用戶資料")
    @GetMapping("/all")
    ResponseEntity<ApiResponse<Object>> getAllUsers() {

        return ResponseEntity.ok(ApiResponse.success("success", userService.findAllUser()));
    }

    @Operation(summary = "用戶註冊", description = "使用者提供基本資訊進行註冊")
    @PostMapping("/register")
    ResponseEntity<ApiResponse<Object>> postRegisterUser(@RequestBody UserDto user) {
        userService.registerUser(user);
        return ResponseEntity.ok(ApiResponse.success("success", null));
    }

    @Operation(summary = "更新使用者資料", description = "透過 ID 更新用戶的基本資訊")
    @PostMapping("/update")
    ResponseEntity<ApiResponse<Object>> postUpdateUser(@RequestBody UserDto user) {
        userService.updateUser(user);
        return ResponseEntity.ok(ApiResponse.success("success", null));
    }

    @Operation(summary = "刪除用戶", description = "根據 userId 刪除指定的使用者")
    @PostMapping("/delete/{userId}")
    ResponseEntity<ApiResponse<Object>> postDeleteUser(
            @Parameter(description = "使用者 ID") @PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("success", null));
    }

    @Operation(summary = "搜尋用戶", description = "根據不同條件（名稱、郵件、手機號碼）查詢使用者")
    @GetMapping("/search/")
    ResponseEntity<ApiResponse<Object>> searchUser(
            @Parameter(description = "查詢類型，如 userName, userEmail, userPhone") @RequestParam String type,
            @Parameter(description = "關鍵字") @RequestParam String keyword) {
        List<Users> user = userService.searchUser(type, keyword);
        return ResponseEntity.ok(ApiResponse.success("success", user));
    }

    @Operation(summary = "根據生日範圍搜尋用戶", description = "提供生日區間，回傳符合條件的使用者")
    @GetMapping("/search/birthdate")
    ResponseEntity<ApiResponse<Object>> searchUserBirthdate(
            @Parameter(description = "起始日期（yyyy-MM-dd）") @RequestParam("from") String from,
            @Parameter(description = "結束日期（yyyy-MM-dd）") @RequestParam("to") String to) {
        List<Users> user = userService.searchUserByBirthdate(from, to);
        return ResponseEntity.ok(ApiResponse.success("success", user));
    }

    @Operation(summary = "進階搜尋用戶", description = "根據 UserAdvancedDto 內的條件進行多層次搜尋")
    @PostMapping("/search/advanced")
    ResponseEntity<ApiResponse<Object>> searchUserAdvanced(@RequestBody UserAdvancedDto user) {
        List<Users> users = userService.searchUserAdvanced(user);
        return ResponseEntity.ok(ApiResponse.success("success", users));
    }

    @Operation(summary = "批量新增用戶", description = "提供多個 User 物件，一次新增多筆使用者資料")
    @PostMapping("/batchAddUser")
    ResponseEntity<ApiResponse<Object>> batchAddUser(@RequestBody List<Users> users) {
        log.info("batchAddUser:{}", users);
        userService.batchAddUser(users);
        return ResponseEntity.ok(ApiResponse.success("success", null));
    }

    @Operation(summary = "管理用戶資料", description = "根據使用者名稱取得 UserManageDto")
    @GetMapping("/manage/")
    ResponseEntity<ApiResponse<Object>> manageUser(
           @RequestHeader("X-User-Id") Integer userId,@RequestHeader("X-User-Name") String userName) {
        log.info("使用者Id:{}和使用者名子:{}", userId, userName);
        UserManageDto dto = userService.getUserManageDto(userName);
        return ResponseEntity.ok(ApiResponse.success("success", dto));
    }

    @Operation(summary = "更新用戶頭像", description = "透過 MultipartFile 上傳使用者頭像")
    @PostMapping("/{userId}/avatar")
    ResponseEntity<ApiResponse<Object>> updateUserAvatar(
            @Parameter(description = "使用者 ID") @PathVariable Integer userId,
            @Parameter(description = "上傳的頭像") @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        userService.updateUserAvatar(userId, avatar);
        return ResponseEntity.ok(ApiResponse.success("success", null));
    }

    @Operation(summary = "管理用戶資料更新", description = "更新 UserManageDto 內的資料")
    @PostMapping("/manage/update/{userId}")
    ResponseEntity<ApiResponse<Object>> updateUser(
            @Parameter(description = "使用者 ID") @PathVariable Integer userId,
            @RequestBody UserManageDto user) {
        userService.updateUserManageDto(user);
        return ResponseEntity.ok(ApiResponse.success("success", null));
    }

    @Operation(summary = "取得用戶歷史記錄", description = "查詢使用者操作歷史，可分頁查詢")
    @GetMapping("/manage/history/")
    ResponseEntity<ApiResponse<Object>> manageUserHistory(
            @RequestHeader("X-User-Id") Integer userId,
            @PageableDefault(size = 5) Pageable pageable) {
        Page<UserHistory> dto = userService.getUserHistoryList(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success("success", dto));
    }

    @GetMapping("/login")
    ResponseEntity<ApiResponse<Object>> loginUser(@RequestParam("userName") String userName,
                                                  @RequestParam("passWord") String passWord
                                                  ){
        try{
            String token =loginService.loginUser(userName, passWord);
            return ResponseEntity.ok(ApiResponse.success("success", token));

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(401,"登入失敗",null));
        }
    }

}
