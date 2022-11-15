package com.housebooking.app.service;

import com.housebooking.app.dao.*;
import com.housebooking.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import java.util.List;

public class AdminService {

	@Autowired
	private AnnouncementRepo announcementRepo;

	@Autowired
	private ReportRepo reportRepo;

	@Autowired
	private TicketRepo ticketRepo;
  
  @Autowired
	private FAQRepo faqRepo;

  @Autowired
  private HomeRepo homeRepo;

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

	public List<TicketModel> findAllTickets() {
		List<TicketModel> tickets = ticketRepo.findAll();
		return tickets;
	}

	public void removeTicket(Long id) {
		ticketRepo.deleteById(id);
	}

	public List<ReportModel> findAllStudentReports() {
		// TODO Auto-generated method stub
		List<ReportModel> reports = reportRepo.findAllByStudentType();
		return reports;
	}
	public void removeStudent(Long id) {
		// TODO Auto-generated method stub
		ReportModel report = reportRepo.findReportById(id);
		System.out.println("report mail==== "+report.getUserMail());
		UserModel user = homeRepo.findbyEmail(report.getUserMail());
		homeRepo.deleteById(user.getId());
		reportRepo.deleteById(id);


	}

}
