package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.ReserveModel;


@Repository
public interface ReserveRepo extends JpaRepository<ReserveModel, Long> {

}
