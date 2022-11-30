package com.housebooking.app.dao;

import com.housebooking.app.model.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.ReviewModel;


@Repository
public interface ReviewRepo extends JpaRepository<ReviewModel, Long> {

}
