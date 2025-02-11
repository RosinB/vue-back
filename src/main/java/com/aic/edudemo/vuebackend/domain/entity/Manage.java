package com.aic.edudemo.vuebackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name="usermanage")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Manage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer manageId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "img")
    private String userImgPath;

    @Column(name = "bto")
    private String  userBio;

    @Column(name ="updated_at")
    private LocalDateTime manageUpdatedAt;
}
