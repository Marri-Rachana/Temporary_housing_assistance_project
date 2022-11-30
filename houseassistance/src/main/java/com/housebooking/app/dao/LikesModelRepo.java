package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.LikesModel;

@Repository
public interface LikesModelRepo extends JpaRepository<LikesModel, Long>{



	@Query( value = "select * from likes where house_id = :houseId and user_id = :userId", nativeQuery = true)
	LikesModel findIsLikedByUser(@Param("houseId") Long houseId,@Param("userId") Long userId);



}
