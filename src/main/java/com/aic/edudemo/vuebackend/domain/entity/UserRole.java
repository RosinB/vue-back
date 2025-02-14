package com.aic.edudemo.vuebackend.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "userrole")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role_id")
    private int roleId;



}
