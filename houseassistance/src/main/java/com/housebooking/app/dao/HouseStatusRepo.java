package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.HouseStatusModel;


@Repository
public interface HouseStatusRepo extends JpaRepository<HouseStatusModel, Long> {



	@Query( value = "select * from house_status where house_property_id = :id", nativeQuery = true)
	HouseStatusModel findHouseStatus(@Param("id") Long id);


}
