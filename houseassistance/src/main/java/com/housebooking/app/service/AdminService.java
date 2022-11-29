package com.housebooking.app.service;

import com.housebooking.app.dao.*;
import com.housebooking.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.util.List;

public class AdminService {

	@Autowired
	private AnnouncementRepo announcementRepo;

	@Autowired
	private ReportRepo reportRepo;

	@Autowired
	private HomeRepo homeRepo;

	@Autowired
	private HouseRepo houseRepo;

	@Autowired
	private HouseStatusRepo houseStatusRepo;

	@Autowired
	private HousePropertiesRepo housePropertiesRepo;

	@Autowired
	private HouseDocumentRepo houseDocsRepo;

	@Autowired
	private TicketRepo ticketRepo;
  
  @Autowired
	private FAQRepo faqRepo;

	@Autowired
	private CouponRepo couponRepo;


	public String addAnnouncement(Announcement announcement) {

		announcementRepo.save(announcement);
		return "Announcement Saved Successfully";
	}

	public List<ReportModel> findAllStudentReports() {
		// TODO Auto-generated method stub
		List<ReportModel> reports = reportRepo.findAllByStudentType();
		return reports;
	}

	public List<ReportModel> findAllHousesReports() {
		List<ReportModel> reports = reportRepo.findAllByOwnerType();
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

	public void removeHouse(Long id) {
		ReportModel report = reportRepo.findReportById(id);
		HouseModel house = houseRepo.findbyHouseName(report.getHouseName());
		houseRepo.deleteById(house.getId());
		reportRepo.deleteById(id);
	}

	public List<TicketModel> findAllTickets() {
		List<TicketModel> tickets = ticketRepo.findAll();
		return tickets;
	}

	public void removeTicket(Long id) {
		ticketRepo.deleteById(id);
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

	public void addCoupon(Coupon coupon) {
		couponRepo.save(coupon);
	}

	public List<HouseDocumentModel> getAllNotVerifiedHouses() {
List<HouseDocumentModel> houseDocs = new ArrayList<HouseDocumentModel>();

		List<HouseStatusModel> houseStatus = houseStatusRepo.findAll();

		houseStatus.forEach(status -> {
			if(status.getIsVerified().equals("0")) {
				houseDocs.add(houseDocsRepo.findHouseDocument(status.getId()));
			}
		});

		return houseDocs;
	}

	public HouseDocumentModel getHouseDocument(Long id) {
		// TODO Auto-generated method stub
		return houseDocsRepo.findHouseDocument(id);
	}

	public void verifyHouse(Long id) {
		// TODO Auto-generated method stub

		HouseStatusModel houseStatus = houseStatusRepo.findHouseStatus(id);

		houseStatus.setIsVerified("1");
		houseStatusRepo.save(houseStatus);

		HousePropertiesModel houseProperty = housePropertiesRepo.findHouseProperties(id);

		houseProperty.setIsVerified("1");

}

}
