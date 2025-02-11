package com.aic.edudemo.vuebackend.controller;


import com.aic.edudemo.vuebackend.domain.dto.UserAdvancedDto;
import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.dto.UserManageDto;
import com.aic.edudemo.vuebackend.domain.entity.User;
import com.aic.edudemo.vuebackend.service.UserService;
import com.aic.edudemo.vuebackend.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping( "users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    ResponseEntity<ApiResponse<Object>> getAllUsers(){
        return ResponseEntity.ok(ApiResponse.success("success",userService.findAllUser()));
    }

    @PostMapping("/register")
    ResponseEntity<ApiResponse<Object>> postRegisterUser(@RequestBody UserDto user){
        userService.registerUser(user);
        return ResponseEntity.ok(ApiResponse.success("success",null));
    }

    @PostMapping("/update")
    ResponseEntity<ApiResponse<Object>> postUpdateUser(@RequestBody UserDto user){

        userService.updateUser(user);
        return ResponseEntity.ok(ApiResponse.success("success",null));
    }

    @PostMapping("/delete/{userId}")
    ResponseEntity<ApiResponse<Object>> postDeleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("success",null));
    }



    @GetMapping("/search/")
    ResponseEntity<ApiResponse<Object>> searchUser(@RequestParam String type,@RequestParam String keyword){

        List<User> user =userService.searchUser(type,keyword);
        return ResponseEntity.ok(ApiResponse.success("success",user));

    }

    @GetMapping("/search/birthdate")
    ResponseEntity<ApiResponse<Object>> searchUserBirthdate(
            @RequestParam("from")String from,
            @RequestParam("to")String to){

        List<User> user =userService.searchUserByBirthdate(from,to);
        return ResponseEntity.ok(ApiResponse.success("success",user));
    }

    @PostMapping("/search/advanced")
    ResponseEntity<ApiResponse<Object>> searchUserAdvanced(@RequestBody UserAdvancedDto user){

        List<User> users =userService.searchUserAdvanced(user);

        return ResponseEntity.ok(ApiResponse.success("success",users));
    }


    @PostMapping("/batchAddUser")
    ResponseEntity<ApiResponse<Object>> batchAddUser(@RequestBody List<User> users){
        log.info("batchAddUser:{}",users);
        userService.batchAddUser(users);
        return ResponseEntity.ok(ApiResponse.success("success",null));


    }

    @GetMapping("/manage/{userName}")
    ResponseEntity<ApiResponse<Object>> manageUser(@PathVariable String userName){
        System.out.println(userName);
        List<UserManageDto> dto = userService.getUserManageDto(userName);
        return ResponseEntity.ok(ApiResponse.success("success",dto));
    }




}
