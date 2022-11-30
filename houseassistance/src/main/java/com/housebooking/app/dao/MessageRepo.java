package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.MessageModel;


@Repository
public interface MessageRepo extends JpaRepository<MessageModel, Long> {

	@Query( value = "select * from messages where id = :id", nativeQuery = true)
	MessageModel findMessageById(@Param("id") Long id);

}
