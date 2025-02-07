package com.aic.edudemo.vuebackend.controller;


import com.aic.edudemo.vuebackend.domain.dto.UserDto;
import com.aic.edudemo.vuebackend.domain.entity.User;
import com.aic.edudemo.vuebackend.service.UserService;
import com.aic.edudemo.vuebackend.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
