package com.aic.edudemo.vuebackend.repository;

import com.aic.edudemo.vuebackend.domain.entity.Pic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PicRepository extends JpaRepository<Pic,Integer> {
}
