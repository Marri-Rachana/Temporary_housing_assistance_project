package com.housebooking.app.service;

import com.housebooking.app.dao.AnnouncementRepo;
import com.housebooking.app.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import com.housebooking.app.dao.FAQRepo;
import com.housebooking.app.model.FAQModel;
import java.util.List;

public class AdminService {

	@Autowired
	private AnnouncementRepo announcementRepo;

	@Autowired
	private FAQRepo faqRepo;

	public String addAnnouncement(Announcement announcement) {

		announcementRepo.save(announcement);
		return "Announcement Saved Successfully";


	}

	public List<FAQModel> findAllFAQs() {
		List<FAQModel> faqs = faqRepo.findAll();
		return faqs;
	}

	public void addFaq(FAQModel faq) {
		faqRepo.save(faq);

	}

	public FAQModel findFAQById(Long id) {
		FAQModel faq = faqRepo.findFAQById(id);
		return faq;
	}

}
