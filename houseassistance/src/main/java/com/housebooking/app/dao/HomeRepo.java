package com.housebooking.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.housebooking.app.model.UserModel;

public interface HomeRepo extends JpaRepository<UserModel, Long>{
	@Query( value = "select * from users where email = :email", nativeQuery = true)
	UserModel findbyEmail(@Param("email") String email);

	@Query( value = "select * from users where usertype = 'student'", nativeQuery = true)
	List<UserModel> findAllStudents();

	@Query( value = "select * from users where usertype = 'houseowner'", nativeQuery = true)
	List<UserModel> findAllOwners();

	@Query( value = "delete from users where email = :userMail", nativeQuery = true)
	void deleteByUserMail(@Param("userMail") String userMail);


}
