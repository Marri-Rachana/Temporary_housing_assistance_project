package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.HousePropertiesModel;


@Repository
public interface HousePropertiesRepo extends JpaRepository<HousePropertiesModel, Long> {


	@Query( value = "select * from house_properties where house_id = :id", nativeQuery = true)
	HousePropertiesModel findHouseProperties(@Param("id") Long id);


}
