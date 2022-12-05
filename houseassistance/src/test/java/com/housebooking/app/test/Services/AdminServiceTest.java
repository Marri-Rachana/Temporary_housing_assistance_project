package com.housebooking.app.test.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.housebooking.app.utils.TestUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;


import com.housebooking.app.service.*;
import com.housebooking.app.model.*;
import com.housebooking.app.dao.*;

import java.util.ArrayList;
import java.util.List;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AnnouncementRepo announcementRepo;

    @Mock
    private ReportRepo reportRepo;

    @Mock
    private HouseDetailsRepo houseDetailsRepo;

    @Mock
    private HomeRepo homeRepo;

    @Mock
    private HouseRepo houseRepo;

    @Mock
    private HouseStatusRepo houseStatusRepo;

    @Mock
    private HouseOwnerService houseOwnerService;

    @Mock
    private HousePropertiesRepo housePropertiesRepo;

    @Mock
    private HouseDocumentRepo houseDocsRepo;

    @Mock
    private TicketRepo ticketRepo;

    @Mock
    private FAQRepo faqRepo;

    @Mock
    private CouponRepo couponRepo;
    @Test
    public void AddAnnouncementTest() {

        Announcement announcement = new Announcement();

        announcement.setAnnouncementDescription("Lotus Community discounts on rent for next month");
        announcement.setAnnouncementTitle("Use NEWYEARSPECIAL coupon code while booking");
        announcement.setStartDate("2022-11-20");
        announcement.setStartTime("10:00");
        when(announcementRepo.save(announcement)).thenReturn(announcement);

        assertEquals("1",adminService.addAnnouncement(announcement));

    }

    @Test
    public void AddAnnouncementExceptionTest() {

        Announcement announcement = new Announcement();

        announcement.setAnnouncementDescription("Lotus Community discounts on rent for next month");
        announcement.setAnnouncementTitle("What is Lorem Ipsum?\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "Why do we use it?\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
                "\n" +
                "Where can I get some?\n" +
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                "\n" +
                "5\n" +
                "\tparagraphs\n" +
                "\twords\n" +
                "\tbytes\n" +
                "\tlists\n" +
                "\tStart with 'Lorem\n" +
                "ipsum dolor sit amet...'\n");
        announcement.setStartDate("2022-11-20");
        announcement.setStartTime("10:00");
        when(announcementRepo.save(announcement)).thenThrow(DataIntegrityViolationException.class);
        assertEquals("0", adminService.addAnnouncement(announcement));
    }

    @Test
    public void removeStudentTest()
    {
        UserModel user = new UserModel();
        user.setId(8l);
        user.setEmail("temp.house@test.com");
        user.setUsername("temp house");
        user.setPassword("temp");
        user.setUsertype("student");
        ReportModel reportModel = new ReportModel();
        reportModel.setId(9l);
        reportModel.setUserMail("temp.house@test.com");
        reportModel.setUserType("student");
        reportModel.setHouseName("new house");
        reportModel.setReason("bad");
        when(reportRepo.findReportById(any())).thenReturn(reportModel);
        when(homeRepo.findbyEmail(any()))
                .thenReturn(user);
        int result = adminService.removeStudent(10l);
        assertEquals(1,result);
    }

    @Test
    public void removeStudentTest_negativeTest()
    {
        when(reportRepo.findReportById(any())).thenThrow(NullPointerException.class);
        int result = adminService.removeStudent(10l);
        assertEquals(0,result);
    }

    @Test
    public void removeTicketTest()
    {
        TicketModel ticket = new TicketModel();
        ticket.setId(3l);
        ticket.setUserMail("test@test.com");
        ticket.setDescription("bad boys");
        int result = adminService.removeTicket(3l);
        assertEquals(1,result);
    }

    @Test
    public void removeTicketTest_Negative()
    {
        TicketModel ticket = new TicketModel();
        ticket.setId(3l);
        ticket.setUserMail("test@test.com");
        ticket.setDescription("bad boys");
        Mockito.doThrow(NullPointerException.class).when(ticketRepo).deleteById(3l);
        int result = adminService.removeTicket(3l);
        assertEquals(0,result);
    }

    @Test
    public void removeSpamHouse_Test()
    {
        HouseDetailsModel house = new HouseDetailsModel();
        house.setId(23l);
        house.setNoOfBedrooms("4");
        house.setHouseName("test_Home");
        ReportModel reportModel = new ReportModel();
        reportModel.setId(9l);
        reportModel.setUserMail("temp.house@test.com");
        reportModel.setUserType("student");
        reportModel.setHouseName("new house");
        reportModel.setReason("bad");
        when(reportRepo.findReportById(any())).thenReturn(reportModel);
        when(houseDetailsRepo.findbyHouseName(any())).thenReturn(house);
        int result = adminService.removeHouse(10l);
        assertEquals(1,result);
    }

    @Test
    public void removeSpamHouse_negativeTest()
    {
        when(reportRepo.findReportById(any())).thenThrow(NullPointerException.class);
        int result = adminService.removeHouse(10l);
        assertEquals(0,result);
    }

    @Test
    public void findAllFAQ_Test()
    {
        FAQModel faq = new FAQModel();
        List<FAQModel> faqs = new ArrayList<>();
        faqs.add(faq);
        when(faqRepo.findAll()).thenReturn(faqs);
        List<FAQModel> result = adminService.findAllFAQs();
        assertEquals(faqs,result);
    }

    @Test
    public void findAllFAQ_NegativeTest()
    {
        FAQModel faq = new FAQModel();
        List<FAQModel> faqs = new ArrayList<>();
        faqs.add(faq);
        when(faqRepo.findAll()).thenThrow(NullPointerException.class);
        List<FAQModel> result = adminService.findAllFAQs();
        assertEquals(null,result);
    }

    @Test
    public void addFaq_Test()
    {
        FAQModel faq = new FAQModel();
        faq.setId(8l);
        faq.setQuestion("test?");
        faq.setAnswer("ans test");
        when(faqRepo.save(faq)).thenReturn(faq);

        assertEquals(1,adminService.addFaq(faq));

    }

    @Test
    public void addFaq_negativeTest()
    {
        FAQModel faq = new FAQModel();
        faq.setId(8l);
        faq.setQuestion("\"What is Lorem Ipsum?\\n\" +\n" +
                "                \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Why do we use it?\\n\" +\n" +
                "                \"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Where does it come from?\\n\" +\n" +
                "                \"Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \\\"de Finibus Bonorum et Malorum\\\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \\\"Lorem ipsum dolor sit amet..\\\", comes from a line in section 1.10.32.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \\\"de Finibus Bonorum et Malorum\\\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Where can I get some?\\n\" +\n" +
                "                \"There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"5\\n\" +\n" +
                "                \"\\tparagraphs\\n\" +\n" +
                "                \"\\twords\\n\" +\n" +
                "                \"\\tbytes\\n\" +\n" +
                "                \"\\tlists\\n\" +\n" +
                "                \"\\tStart with 'Lorem\\n\" +\n" +
                "                \"ipsum dolor sit amet...'\\n\"");
        faq.setAnswer("ans test");
        when(faqRepo.save(faq)).thenThrow(DataIntegrityViolationException.class);

        assertEquals(0,adminService.addFaq(faq));

    }

    @Test
    public void findFAQByID_Test()
    {
        FAQModel faq = new FAQModel();
        faq.setId(8l);
        when(faqRepo.findFAQById(any())).thenReturn(faq);
        FAQModel result = adminService.findFAQById(8l);
        assertEquals(faq,result);
    }

    @Test
    public void findFAQByID_negativeTest()
    {
        when(faqRepo.findFAQById(any())).thenThrow(NullPointerException.class);
        FAQModel result = adminService.findFAQById(8l);
        assertEquals(null,result);
    }

    @Test
    public void addCoupon_Test()
    {
        Coupon coupon = new Coupon();
        coupon.setId(1l);
        coupon.setCouponTitle("test");
        coupon.setCouponCode("test");
        coupon.setDiscountAmount("500");
        when(couponRepo.save(coupon)).thenReturn(coupon);
        int result = adminService.addCoupon(coupon);
        assertEquals(1,result);
    }

    @Test
    public void addCoupon_negativeTest()
    {
        Coupon coupon = new Coupon();
        coupon.setId(1l);
        coupon.setCouponTitle("test");
        coupon.setCouponCode("test");
        coupon.setDiscountAmount("500");
        when(couponRepo.save(coupon)).thenThrow(NullPointerException.class);
        int result = adminService.addCoupon(coupon);
        assertEquals(0,result);
    }

    @Test
    public void findAllStudentsReports_Test()
    {
        List<ReportModel> report = new ArrayList<>();
        ReportModel reportModel = new ReportModel();
        reportModel.setId(9l);
        reportModel.setUserMail("temp.house@test.com");
        reportModel.setUserType("houseowner");
        reportModel.setHouseName("new house");
        reportModel.setReason("bad");
        report.add(reportModel);
        when(reportRepo.findAllByStudentType()).thenReturn(report);
        List<ReportModel> result = adminService.findAllStudentReports();
        assertEquals(report,result);

    }
    @Test
    public void findAllStudentsReports_negativeTest()
    {
        List<ReportModel> report = new ArrayList<>();
        when(reportRepo.findAllByStudentType()).thenThrow(NullPointerException.class);
        List<ReportModel> result = adminService.findAllStudentReports();
        assertEquals(null,result);

    }
    @Test
    public void findAllHousesReports_Test() {
        List<ReportModel> report = new ArrayList<>();
        ReportModel reportModel = new ReportModel();
        reportModel.setId(12l);
        reportModel.setUserMail("temp.house@test.com");
        reportModel.setUserType("student");
        reportModel.setHouseName("new house");
        reportModel.setReason("bad");
        report.add(reportModel);
        when(reportRepo.findAllByOwnerType()).thenReturn(report);
        List<ReportModel> result = adminService.findAllHousesReports();
        assertEquals(report,result);

    }

    @Test
    public void findAllhousesReports_negativeTest()
    {
        List<ReportModel> report = new ArrayList<>();
        when(reportRepo.findAllByOwnerType()).thenThrow(NullPointerException.class);
        List<ReportModel> result = adminService.findAllHousesReports();
        assertEquals(null,result);

    }

    @Test
    public void findAllTickets_Test() {
        List<TicketModel> ticket = new ArrayList<>();
        TicketModel ticketModel = new TicketModel();
        ticketModel.setId(1l);
        ticketModel.setUserMail("test@test.com");
        ticketModel.setDescription("issuetest");
        ticket.add(ticketModel);
        when(ticketRepo.findAll()).thenReturn(ticket);
        List<TicketModel> result = adminService.findAllTickets();
        assertEquals(ticket,result);
    }

    @Test
    public void findAllTickets_negativeTest() {
        when(ticketRepo.findAll()).thenThrow(NullPointerException.class);
        List<TicketModel> result = adminService.findAllTickets();
        assertEquals(null,result);
    }

    @Test
    public void verifyHouse_Test()
    {
        HouseStatusModel houseStatusModel = new HouseStatusModel();
        HousePropertiesModel housePropertiesModel = new HousePropertiesModel();
        housePropertiesModel.setIsVerified("1");
        houseStatusModel.setId(3l);
        houseStatusModel.setIsBooked("0");
        houseStatusModel.setIsHide("1");
        houseStatusModel.setIsVerified("0");
        when(houseStatusRepo.findHouseStatus(any())).thenReturn(houseStatusModel);
        when(housePropertiesRepo.findHouseProperties(any())).thenReturn(housePropertiesModel);

        int result = adminService.verifyHouse(3l);
        assertEquals(1,result);
    }

    @Test
    public void verifyHouse_negativeTest()
    {
        HouseStatusModel houseStatusModel = new HouseStatusModel();
        HousePropertiesModel housePropertiesModel = new HousePropertiesModel();
        housePropertiesModel.setIsVerified("1");
        houseStatusModel.setId(3l);
        houseStatusModel.setIsBooked("0");
        houseStatusModel.setIsHide("1");
        houseStatusModel.setIsVerified("0");
        when(houseStatusRepo.findHouseStatus(any())).thenThrow(NullPointerException.class);

        int result = adminService.verifyHouse(3l);
        assertEquals(0,result);
    }

    @Test
    public void getHouseDocument_Test()
    {
        when(houseDocsRepo.findHouseDocument(any())).thenReturn(TestUtils.getHouseDocumentModel());
        HouseDocumentModel houseDocumentModel = adminService.getHouseDocument(123L);
        assertEquals(TestUtils.getHouseDocumentModel(),houseDocumentModel);
    }

    @Test
    public void getHouseDocument_negativeTest()
    {
        when(houseDocsRepo.findHouseDocument(any())).thenThrow(NullPointerException.class);
        HouseDocumentModel houseDocumentModel = adminService.getHouseDocument(123L);
        assertEquals(null,houseDocumentModel);
    }

    @Test
    public  void getAllNotVerifiedHouses_Test()
    {
        List<HouseDocumentModel>  houseDocs = new ArrayList<HouseDocumentModel>();
        houseDocs.add(TestUtils.getHouseDocumentModel());
        List<HouseStatusModel> houseStatusModels = new ArrayList<>();
        houseStatusModels.add(TestUtils.getHouseStatusModel());
        when(houseStatusRepo.findAll()).thenReturn(houseStatusModels);
        when(houseDocsRepo.findHouseDocument(any())).thenReturn(TestUtils.getHouseDocumentModel());
        List<HouseDocumentModel>  houseDocumentModelsResult = adminService.getAllNotVerifiedHouses();
        assertEquals(houseDocs,houseDocumentModelsResult);

    }

    @Test
    public  void getAllNotVerifiedHouses_negativeTest()
    {
        List<HouseDocumentModel>  houseDocs = new ArrayList<HouseDocumentModel>();
        houseDocs.add(TestUtils.getHouseDocumentModel());
        List<HouseStatusModel> houseStatusModels = new ArrayList<>();
        houseStatusModels.add(TestUtils.getHouseStatusModel());
        when(houseStatusRepo.findAll()).thenThrow(NullPointerException.class);
        List<HouseDocumentModel>  houseDocumentModelsResult = adminService.getAllNotVerifiedHouses();
        assertEquals(null,houseDocumentModelsResult);

    }
}