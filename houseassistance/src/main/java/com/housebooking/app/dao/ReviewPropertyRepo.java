package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.ReviewPropertyModel;


@Repository
public interface ReviewPropertyRepo extends JpaRepository<ReviewPropertyModel, Long> {

}
