package com.aic.edudemo.vuebackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "userhistory") // 對應資料表 user_history
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動遞增
    private Long id; // 主鍵

    @Column(name = "user_name")
    private String userName; // 使用者名字

    @Column(name="user_id")
    private Integer userId;


    @Column(name = "user_phone", length = 20)
    private String userPhone; // 使用者電話

    @Column(name = "user_email", length = 100)
    private String userEmail; // 使用者信箱

    @Column(name = "user_idcard", length = 20)
    private String userIdCard; // 身分證

    @Column(name = "user_birth_date")
    private LocalDate userBirthDate; // 出生日期



    @Column(name = "user_bio", columnDefinition = "TEXT")
    private String userBio; // 使用者自我介紹



    @Column(name = "createat")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private java.time.LocalDateTime createAt = java.time.LocalDateTime.now(); // 創建時間
}