package com.aic.edudemo.vuebackend.domain.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDto {



    private Integer userId;

    private String userName;

    private String userPwd;


    private String userPwdHash;

    private String userPhone;

    private String userEmail;

    private String userIdCard;

    private LocalDate userBirthDate;


}
