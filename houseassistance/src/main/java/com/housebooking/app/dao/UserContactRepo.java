package com.housebooking.app.dao;

import com.housebooking.app.model.UserContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserContactRepo extends JpaRepository<UserContactModel, Long> {

}
