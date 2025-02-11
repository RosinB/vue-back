package com.aic.edudemo.vuebackend.repository;

import com.aic.edudemo.vuebackend.domain.entity.Manage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManageRepository  extends JpaRepository<Manage,Integer> {

}
