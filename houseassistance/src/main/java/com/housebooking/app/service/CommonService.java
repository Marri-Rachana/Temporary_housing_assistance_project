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
	
	public int saveReview(ReviewModel review) {
		try {
			// TODO Auto-generated method stub
			reviewRepo.save(review);
		}
		catch (Exception e)
		{
			return 0;
		}
		return 1;
	}

	public int saveTicket(TicketModel ticket) {
		// TODO Auto-generated method stub
		try {
			ticketRepo.save(ticket);
		}
		catch (Exception e)
		{
			return 0;
		}
		return 1;
	}

}
