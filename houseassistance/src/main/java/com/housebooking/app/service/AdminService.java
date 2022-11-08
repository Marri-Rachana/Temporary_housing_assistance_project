package com.housebooking.app.service;

import com.housebooking.app.dao.AnnouncementRepo;
import com.housebooking.app.dao.TicketRepo;
import com.housebooking.app.model.Announcement;
import com.housebooking.app.model.TicketModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminService {

	@Autowired
	private AnnouncementRepo announcementRepo;

	@Autowired
	private TicketRepo ticketRepo;

	public String addAnnouncement(Announcement announcement) {

		announcementRepo.save(announcement);
		return "Announcement Saved Successfully";
	}

	public List<TicketModel> findAllTickets() {
		List<TicketModel> tickets = ticketRepo.findAll();
		return tickets;
	}

	public void removeTicket(Long id) {
		ticketRepo.deleteById(id);
	}

}
