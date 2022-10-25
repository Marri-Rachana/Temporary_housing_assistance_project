package com.housebooking.app.service;

import com.housebooking.app.dao.AnnouncementRepo;
import com.housebooking.app.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminService {

	@Autowired
	private AnnouncementRepo announcementRepo;

	public String addAnnouncement(Announcement announcement) {

		announcementRepo.save(announcement);
		return "Announcement Saved Successfully";
	}

}
