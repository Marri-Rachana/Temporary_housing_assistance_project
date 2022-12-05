package com.housebooking.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.housebooking.app.dao.AnnouncementRepo;
import com.housebooking.app.dao.CouponRepo;
import com.housebooking.app.dao.FAQRepo;
import com.housebooking.app.dao.HomeRepo;
import com.housebooking.app.dao.HouseDetailsRepo;
import com.housebooking.app.dao.HouseDocumentRepo;
import com.housebooking.app.dao.HousePropertiesRepo;
import com.housebooking.app.dao.HouseRepo;
import com.housebooking.app.dao.HouseStatusRepo;
import com.housebooking.app.dao.ReportRepo;
import com.housebooking.app.dao.TicketRepo;
import com.housebooking.app.model.Announcement;
import com.housebooking.app.model.Coupon;
import com.housebooking.app.model.FAQModel;
import com.housebooking.app.model.HouseDetailsModel;
import com.housebooking.app.model.HouseDocumentModel;
import com.housebooking.app.model.HouseModel;
import com.housebooking.app.model.HousePropertiesModel;
import com.housebooking.app.model.HouseStatusModel;
import com.housebooking.app.model.ReportModel;
import com.housebooking.app.model.TicketModel;
import com.housebooking.app.model.UserModel;

@Service
public class AdminService {

	@Autowired
	private AnnouncementRepo announcementRepo;

	@Autowired
	private ReportRepo reportRepo;

	@Autowired
	private HouseDetailsRepo houseDetailsRepo;

	@Autowired
	private HomeRepo homeRepo;

	@Autowired
	private HouseRepo houseRepo;

	@Autowired
	private HouseStatusRepo houseStatusRepo;

	@Autowired
	private HouseOwnerService houseOwnerService;

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

		try {
			announcementRepo.save(announcement);
		}
		catch(Exception e)
		{
			return "0";
		}
		return "1";
	}

	public List<ReportModel> findAllStudentReports() {
		// TODO Auto-generated method stub
		List<ReportModel> reports;
		try {
			 reports = reportRepo.findAllByStudentType();
		}
		catch(Exception e)
		{
			return null;
		}
		return reports;
	}

	public List<ReportModel> findAllHousesReports() {
		List<ReportModel> reports;
		try {
			reports = reportRepo.findAllByOwnerType();
		}
		catch(Exception e)
		{
			return null;
		}
		return reports;
	}

	public int removeStudent(Long id) {
		// TODO Auto-generated method stub
		try {
			ReportModel report = reportRepo.findReportById(id);
			System.out.println("report mail==== " + report.getUserMail());
			UserModel user = homeRepo.findbyEmail(report.getUserMail());
			homeRepo.deleteById(user.getId());
			reportRepo.deleteById(id);
		}
		catch (Exception e)
		{
			return 0;
		}
		return 1;

	}

	public int removeHouse(Long id) {
            try {
				ReportModel report = reportRepo.findReportById(id);
				HouseDetailsModel house = houseDetailsRepo.findbyHouseName(report.getHouseName());
				//System.out.println(report.getHouseName());
				//System.out.println(house.getId());
				houseOwnerService.deleteHouse(house.getId());
				reportRepo.deleteById(id);
			}
			catch (Exception e)
			{
				return 0;
			}

			return 1;

	}

	public List<TicketModel> findAllTickets() {
		List<TicketModel> tickets;
		try {
			tickets = ticketRepo.findAll();
		}
		catch(Exception e)
		{
			return null;
		}
		return tickets;
	}

	public int removeTicket(Long id) {
		try {
			ticketRepo.deleteById(id);
		}
		catch (Exception e)
		{
			return 0;
		}
		return 1;
	}

	public List<FAQModel> findAllFAQs() {
		List<FAQModel> faqs;
		try {
			 faqs = faqRepo.findAll();
		}
		catch (Exception e)
		{
			return null;
		}
		return faqs;
	}

	public int addFaq(FAQModel faq) {
		try {
			faqRepo.save(faq);
		}
		catch (Exception e)
		{
			return 0;
		}
		return 1;

	}

	public FAQModel findFAQById(Long id) {
		FAQModel faq;
		try {
			 faq = faqRepo.findFAQById(id);
		}
		catch (Exception e)
		{
			return null;
		}
		return faq;
	}

	public int addCoupon(Coupon coupon) {
		// TODO Auto-generated method stub
		try {
			couponRepo.save(coupon);
		}
		catch(Exception e)
		{
			return 0;
		}
		return 1;
	}

	public List<HouseDocumentModel> getAllNotVerifiedHouses() {
		List<HouseDocumentModel>  houseDocs = new ArrayList<HouseDocumentModel>();
		try {
			List<HouseStatusModel> houseStatus = houseStatusRepo.findAll();

			houseStatus.forEach(status -> {
				if (status.getIsVerified().equals("0")) {
					houseDocs.add(houseDocsRepo.findHouseDocument(status.getId()));
				}
			});
		}
		catch (Exception e)
		{
			return null;
		}

		return houseDocs;
	}

	public HouseDocumentModel getHouseDocument(Long id) {
		// TODO Auto-generated method stub
		HouseDocumentModel houseDocumentModel;
		try {
			houseDocumentModel = houseDocsRepo.findHouseDocument(id);
		}
		catch (Exception e)
		{
			return null;
		}
		return  houseDocumentModel;
	}

	public int verifyHouse(Long id) {
		// TODO Auto-generated method stub
        try {
			HouseStatusModel houseStatus = houseStatusRepo.findHouseStatus(id);

			houseStatus.setIsVerified("1");
			houseStatusRepo.save(houseStatus);

			HousePropertiesModel houseProperty = housePropertiesRepo.findHouseProperties(id);

			houseProperty.setIsVerified("1");
		}
		catch (Exception e)
		{
			return 0;
		}
		return 1;

	}

}
