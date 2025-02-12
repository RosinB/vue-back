package com.aic.edudemo.vuebackend.repository;

import com.aic.edudemo.vuebackend.domain.entity.UserHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

    Page<UserHistory> findByUserIdOrderByCreateAtDesc(Integer userId,Pageable pageable);

}
