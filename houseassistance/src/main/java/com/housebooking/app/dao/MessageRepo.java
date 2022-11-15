package com.housebooking.app.dao;

import com.housebooking.app.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepo extends JpaRepository<MessageModel, Long> {

	@Query( value = "select * from messages where id = :id", nativeQuery = true)
	MessageModel findMessageById(Long id);

}
