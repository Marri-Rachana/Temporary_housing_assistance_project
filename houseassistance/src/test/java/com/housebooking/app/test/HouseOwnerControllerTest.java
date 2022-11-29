package com.housebooking.app.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.housebooking.app.dao.HouseRepo;
import com.housebooking.app.dao.MessageRepo;
import com.housebooking.app.dao.ReportRepo;
import com.housebooking.app.dao.ReviewRepo;
import com.housebooking.app.dao.TicketRepo;
import com.housebooking.app.model.HouseModel;
import com.housebooking.app.model.MessageModel;
import com.housebooking.app.model.ReportModel;
import com.housebooking.app.model.ReviewModel;
import com.housebooking.app.model.TicketModel;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class HouseOwnerControllerTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private HouseRepo houseRepo;


    @Test
    public void shouldPostHouse() {

        HouseModel house = new HouseModel();

        house.setCity("vizag");
        house.setDocument("housedocumets");
        house.setAvailableFrom("2022-11-22");
        house.setHouseAddress("Ashokpuram street");
        house.setHouseContact("9359578391");
        house.setHouseDetails("its very spacious house with good eminities");
        house.setHouseName("goodhome apartment");
        house.setHouseOwnerMail("vimal@gmail.com");
        house.setHousePhoto("houseimage");
        house.setHouseRent("15000");
        house.setHouseType("3bhk");
        house.setLawn("no");
        house.setPetFriendly("yes");
        house.setParking("yes");

        List<HouseModel> housedetails = houseRepo.findAll();

        assertThat(housedetails).isEmpty();

        houseRepo.save(house);

        List<HouseModel> houses = houseRepo.findAll();

        assertThat(houses).hasSize(1);
    }


    @Test
    public void shouldReportStudent() {

        ReportModel report = new ReportModel();

        report.setUserMail("jwala@gmail.com");
        report.setUserType("student");
        report.setHouseName("");
        report.setReason("fake student");

        List<ReportModel> reportdetails = reportRepo.findAll();

        assertThat(reportdetails).isEmpty();

        reportRepo.save(report);

        List<ReportModel> reports = reportRepo.findAll();

        assertThat(reports).hasSize(1);

    }

    @Test
    public void shouldReviewApplication() {

        ReviewModel review = new ReviewModel();

        review.setUserMail("shukla@gmail.com");
        review.setDescription("Nice app for house posting");
        review.setRating("5");

        List<ReviewModel> reviewdetails = reviewRepo.findAll();

        assertThat(reviewdetails).isEmpty();

        reviewRepo.save(review);

        List<ReviewModel> reviews = reviewRepo.findAll();

        assertThat(reviews).hasSize(1);

    }

    @Test
    public void shouldRaiseTicket() {

        TicketModel ticket = new TicketModel();

        ticket.setUserMail("swapna@gmail.com");
        ticket.setDescription("my account got locked");

        List<TicketModel> reviewdetails = ticketRepo.findAll();

        assertThat(reviewdetails).isEmpty();

        ticketRepo.save(ticket);

        List<TicketModel> tickets = ticketRepo.findAll();

        assertThat(tickets).hasSize(1);

    }

    @Test
    public void shouldReplyToStudent() {

        MessageModel message = new MessageModel();
        message.setStudentMail("vamshi@gmail.com");
        message.setOwnerMail("sudheer@gmail.com");
        message.setQuestion("how many years old house is this?");
        message.setAnswer("4years");

        List<MessageModel> messagedetail = messageRepo.findAll();
        assertThat(messagedetail).isEmpty();
        messageRepo.save(message);

        List<MessageModel> messages = messageRepo.findAll();
        assertThat(messages).hasSize(1);
    }


}


