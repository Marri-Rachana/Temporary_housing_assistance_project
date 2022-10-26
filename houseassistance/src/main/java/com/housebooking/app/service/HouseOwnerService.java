package com.housebooking.app.service;

import com.housebooking.app.dao.HouseRepo;
import com.housebooking.app.dao.ReportRepo;
import com.housebooking.app.dao.ReviewRepo;
import com.housebooking.app.model.HouseModel;
import com.housebooking.app.model.ReportModel;
import com.housebooking.app.model.ReviewModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HouseOwnerService {

    @Autowired
    private HouseRepo houseRepo;

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private ReviewRepo reviewRepo;
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

}
