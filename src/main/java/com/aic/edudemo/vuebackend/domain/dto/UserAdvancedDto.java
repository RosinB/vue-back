package com.aic.edudemo.vuebackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdvancedDto {

    private String userName;

    private String userPhone;

    private String userEmail;

    private String userIdCard;

    private LocalDate userBirthDateStart;

    private LocalDate userBirthDateEnd;

    private Boolean  userIsVerified;

}
