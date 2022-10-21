package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.housebooking.app.model.UserModel;

public interface HomeRepo extends JpaRepository<UserModel, Long>{
	@Query( value = "select * from users where email = :email", nativeQuery = true)
	UserModel findbyEmail(String email);

}
