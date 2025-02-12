package com.aic.edudemo.vuebackend.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserManageDto {

    private Integer userId;

    private String userName;

    private String userPhone;

    private String userEmail;

    private String userIdCard;

    private LocalDate userBirthDate;

    private Boolean userIsVerified ;

    private String userBio;
    private String userImgPath;


}
