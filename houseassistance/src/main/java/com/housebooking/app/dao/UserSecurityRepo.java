package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.UserSecurityModel;



@Repository
public interface UserSecurityRepo extends JpaRepository<UserSecurityModel, Long> {


    @Query( value = "select * from user_security where user_id = :id", nativeQuery = true)
    UserSecurityModel findUserSecurity(@Param("id") Long id);

}
