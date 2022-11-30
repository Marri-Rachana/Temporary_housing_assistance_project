package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.housebooking.app.model.TicketModel;


@Repository
public interface TicketRepo extends JpaRepository<TicketModel, Long> {

}
