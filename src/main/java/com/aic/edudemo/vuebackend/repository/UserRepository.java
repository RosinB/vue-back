package com.aic.edudemo.vuebackend.repository;

import com.aic.edudemo.vuebackend.domain.dto.UserManageDto;
import com.aic.edudemo.vuebackend.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<Users, Integer> {

    @Query(value = """
                select * 
                from users
                where   user_id like %:value% 
                   or   user_name like  %:value% 
                   or   user_phone like %:value%
                   or   user_email like %:value%
                   or   user_idcard like %:value%
          """, nativeQuery = true
    )
     List<Users> findUserByAllLike(@Param("value") String value);


    List<Users> findByUserNameContaining(String userName);

    List<Users> findByUserEmailContaining(String userEmail);

    @Query("select u.userId from Users u where u.userName = :userName")
    Integer findUserIdByUserName(@Param("userName") String userName);

    List<Users> findByUserPhoneContaining(String userPhone);

    List<Users> findByUserIdCardContaining(String userIdCard);

    List<Users> findByUserBirthDateBetween(LocalDate userBirthDateAfter, LocalDate userBirthDateBefore);


    @Query(value = """
    select *
    from users
    where (:userName is null or user_name like concat('%', :userName, '%'))
    and (:userPhone is null or user_phone like concat('%', :userPhone, '%'))
    and (:userEmail is null or user_email like concat('%', :userEmail, '%'))
    and (:userIdCard is null or user_idcard like concat('%', :userIdCard, '%'))
    and (:userIsVerified is null or user_is_verified = :userIsVerified)
    and (
        (:userBirthDateStart is null and :userBirthDateEnd is null)
        or (:userBirthDateStart is null and user_birth_date <= :userBirthDateEnd)
        or (:userBirthDateStart <= user_birth_date and :userBirthDateEnd is null)
        or (user_birth_date between :userBirthDateStart and :userBirthDateEnd)
    )
    """, nativeQuery = true)
    List<Users> findByAdvancedSearch(
            @Param("userName") String userName,
            @Param("userPhone") String userPhone,
            @Param("userEmail") String userEmail,
            @Param("userIdCard") String userIdCard,
            @Param("userIsVerified") Boolean userIsVerified,
            @Param("userBirthDateStart") LocalDate userBirthDateStart,
            @Param("userBirthDateEnd") LocalDate userBirthDateEnd
    );


    @Query( """
            select  new com.aic.edudemo.vuebackend.domain.dto.UserManageDto(
            u.userId ,u.userName,u.userPhone ,u.userEmail,
            u.userIdCard,u.userBirthDate,u.userIsVerified,m.userBio,m.userImgPath)
            from Users u
            join Manage  m on u.userId = m.userId
            where u.userId = :userId
""")
    UserManageDto findUserManageDto(@Param("userId") Integer userId);

    @Query("select u.userPwdHash from Users u where u.userName =:userName")
    String findUserPwdHashByUserName(@Param("userName") String userName);


}
