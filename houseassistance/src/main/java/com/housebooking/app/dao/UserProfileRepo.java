package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.UserProfileModel;


@Repository
public interface UserProfileRepo extends JpaRepository<UserProfileModel, Long> {


	@Query( value = "select * from user_profiles where user_id = :id", nativeQuery = true)
	UserProfileModel findUserProfile(@Param("id") Long id);

}
