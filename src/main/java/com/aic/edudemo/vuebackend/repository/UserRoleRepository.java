package com.aic.edudemo.vuebackend.repository;

import com.aic.edudemo.vuebackend.domain.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {



    @Query("select r.roleName from UserRole u join Role  r  on u.roleId=r.roleId where u.userId =:userId")
    List<String> findRoleNameByUserId(@Param("userId") Integer userId);
}
