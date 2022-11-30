package com.housebooking.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.ReviewOwnerModel;


@Repository
public interface ReviewOwnerRepo extends JpaRepository<ReviewOwnerModel, Long> {


	@Query( value = "select * from review_owners where owner_mail = :email", nativeQuery = true)
	List<ReviewOwnerModel> findMyAllReviews(@Param("email") String email);

}
