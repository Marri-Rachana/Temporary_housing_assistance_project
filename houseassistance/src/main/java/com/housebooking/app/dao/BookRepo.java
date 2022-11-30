package com.housebooking.app.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.BookModel;


@Repository
public interface BookRepo extends JpaRepository<BookModel, Long> {


	@Query( value = "select * from bookings where user_id = :id", nativeQuery = true)
	List<BookModel> getAllBookingsOfUser(@Param("id") Long id);

}
