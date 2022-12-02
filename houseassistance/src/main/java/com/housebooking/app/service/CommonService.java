package com.housebooking.app.service;

import com.housebooking.app.dao.ReviewRepo;
import com.housebooking.app.dao.TicketRepo;
import com.housebooking.app.model.ReviewModel;
import com.housebooking.app.model.TicketModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	
	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
	private TicketRepo ticketRepo;
	
	public void saveReview(ReviewModel review) {
		// TODO Auto-generated method stub
		reviewRepo.save(review);
	}

	public void saveTicket(TicketModel ticket) {
		// TODO Auto-generated method stub
		ticketRepo.save(ticket);
		
	}

}
