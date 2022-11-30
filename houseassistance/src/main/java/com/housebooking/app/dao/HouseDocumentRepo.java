package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.HouseDocumentModel;


@Repository
public interface HouseDocumentRepo extends JpaRepository<HouseDocumentModel, Long> {


	@Query( value = "select * from house_documents where house_id = :id", nativeQuery = true)
	HouseDocumentModel findHouseDocument(@Param("id") Long id);


}
