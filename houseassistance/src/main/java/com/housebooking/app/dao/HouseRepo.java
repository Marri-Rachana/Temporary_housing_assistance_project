package com.housebooking.app.dao;

import com.housebooking.app.model.HouseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseRepo extends JpaRepository<HouseModel, Long>{

	@Query( value = "select * from houses where house_owner_mail = :email", nativeQuery = true)
	List<HouseModel> findAllByEmailId(@Param("email") String email);

	@Query( value = "select * from houses where id = :id", nativeQuery = true)
	HouseModel findHouseById(@Param("id") Long id);

	@Query( value = "select * from houses where house_name = :houseName", nativeQuery = true)
	HouseModel findbyHouseName(@Param("houseName") String houseName);
}
