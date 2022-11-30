package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.DislikesModel;

@Repository
public interface DislikesModelRepo extends JpaRepository<DislikesModel, Long>{


	@Query( value = "select * from dislikes where house_id = :houseId and user_id = :userId", nativeQuery = true)
	DislikesModel findIsDisLikedByUser(@Param("houseId") Long houseId,@Param("userId") Long userId);



}
