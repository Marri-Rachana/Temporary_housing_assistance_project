package com.housebooking.app.dao;

import com.housebooking.app.model.ReviewOwnerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewOwnerRepo extends JpaRepository<ReviewOwnerModel, Long> {

}
