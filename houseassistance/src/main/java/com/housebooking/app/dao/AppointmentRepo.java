package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.AppointmentModel;



@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentModel, Long> {

}
