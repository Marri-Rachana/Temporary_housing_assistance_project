package com.housebooking.app.service;

import com.housebooking.app.dao.*;
import com.housebooking.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class HouseOwnerService {

    @Autowired
    private HouseRepo houseRepo;

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private MessageRepo messageRepo;
    public void saveHouse(HouseModel house) {

        houseRepo.save(house);
    }

    public List<HouseModel> getAllHousesByEmail(String emailId) {
        List<HouseModel> houses = houseRepo.findAllByEmailId(emailId);

//		houses.forEach(house -> house.setHouseImage(Base64.getEncoder().encodeToString(house.getHousePhoto())));
        return houses;
    }

    public HouseModel getHouseById(Long id) {
        // TODO Auto-generated method stub

        HouseModel house = houseRepo.findHouseById(id);
        System.out.println(house.getHouseAddress());
        return house;
    }

    public void deleteHouse(Long id) {
        // TODO Auto-generated method stub
        houseRepo.deleteById(id);
    }

    public void saveReport(ReportModel report) {
        // TODO Auto-generated method stub
        reportRepo.save(report);
    }

    public void saveReview(ReviewModel review) {
        // TODO Auto-generated method stub
        reviewRepo.save(review);
    }

    public void saveTicket(TicketModel ticket) {
        // TODO Auto-generated method stub
        ticketRepo.save(ticket);

    }

    public MessageModel getMsgById(Long id) {
        // TODO Auto-generated method stub
        return messageRepo.findMessageById(id);

    }

    public List<MessageModel> findAllMessages(String email) {
        // TODO Auto-generated method stub
        List<MessageModel> msgs = messageRepo.findAll();
        List<MessageModel> studentMsgs = msgs.stream().filter(msg -> msg.getOwnerMail().equals(email) && msg.getAnswer().equals("")).collect(Collectors.toList());
        return studentMsgs;
    }

}
