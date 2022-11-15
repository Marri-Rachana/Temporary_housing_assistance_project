package com.housebooking.app.dao;

import com.housebooking.app.model.UserProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserProfileRepo extends JpaRepository<UserProfileModel, Long> {

}
