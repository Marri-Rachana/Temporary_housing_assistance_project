package com.housebooking.app.dao;

import com.housebooking.app.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<TicketModel, Long> {

}
