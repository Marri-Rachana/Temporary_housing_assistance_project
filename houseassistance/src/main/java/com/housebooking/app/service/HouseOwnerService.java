package com.housebooking.app.service;

import com.housebooking.app.dao.HouseRepo;
import com.housebooking.app.model.HouseModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HouseOwnerService {

    @Autowired
    private HouseRepo houseRepo;
    public void saveHouse(HouseModel house) {

        houseRepo.save(house);
    }

    public List<HouseModel> getAllHousesByEmail(String emailId) {
        List<HouseModel> houses = houseRepo.findAllByEmailId(emailId);

//		houses.forEach(house -> house.setHouseImage(Base64.getEncoder().encodeToString(house.getHousePhoto())));
        return houses;
    }

}
