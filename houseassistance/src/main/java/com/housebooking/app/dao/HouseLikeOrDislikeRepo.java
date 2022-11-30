package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.HouseLikeOrDislikeModel;


@Repository
public interface HouseLikeOrDislikeRepo extends JpaRepository<HouseLikeOrDislikeModel, Long> {

	@Query( value = "select * from house_like_and_dislikes where id = :id", nativeQuery = true)
	HouseLikeOrDislikeModel findLikeOrDislikeById(@Param("id") Long id);

}
