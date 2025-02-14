package com.aic.edudemo.vuebackend.repository;

import com.aic.edudemo.vuebackend.domain.entity.Verified;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface VerifiedRepository  extends JpaRepository<Verified, Integer> {


    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Verified v WHERE v.userId = :userId AND v.status = '審核中'")
    boolean existsByUserIdAndStatus(@Param("userId") Integer userId);


    @Query("select  v from Verified  v  where v.userId=:userId")
    Verified findByUserId(@Param("userId") Integer userId);

    @Query("select v from Verified  v where v.status='審核中'")
    List<Verified> findAllByStatus();

    @Query("select v from Verified  v where v.status <>'審核中'")
    List<Verified> findAllByHistory();
}
