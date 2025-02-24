package com.aic.edudemo.vuebackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate  // ✅ 只更新有改變的欄位，沒設定的欄位不會被影響
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_pswd_hash")
    private String userPwdHash;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_idcard")
    private String userIdCard;

    @Column(name ="user_birth_date")
    private LocalDate userBirthDate;


    @Column(name = "user_regdate" ,insertable = false, updatable = false)
    private Timestamp userRegdate;

    @Column(name = "user_is_verified")
    private Boolean userIsVerified = false;  // 給預設值


}