package com.housebooking.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.HouseDetailsModel;
import com.housebooking.app.model.UserProfileModel;


@Repository
public interface HouseDetailsRepo extends JpaRepository<HouseDetailsModel, Long> {


	@Query( value = "select * from house_details where house_id = :id", nativeQuery = true)
	HouseDetailsModel findHouseDetails(@Param("id") Long id);



}
