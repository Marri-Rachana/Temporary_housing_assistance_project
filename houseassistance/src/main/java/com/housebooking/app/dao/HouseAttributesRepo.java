package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.HouseAttributesModel;


@Repository
public interface HouseAttributesRepo extends JpaRepository<HouseAttributesModel, Long> {


	@Query( value = "select * from house_attributes where house_details_id = :id", nativeQuery = true)
	HouseAttributesModel findHouseAttributes(@Param("id") Long id);

}
