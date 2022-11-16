package com.housebooking.app.service;

import com.housebooking.app.dao.*;
import com.housebooking.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserService {

    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private HouseRepo houseRepo;

    @Autowired
    private AppointmentModel appointmentModel;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private ReserveModel reserveModel;

    @Autowired
    private Coupon coupon;

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private ReserveRepo reserveRepo;

    @Autowired
    private FavouritesRepo favouritesRepo;

    @Autowired
    private ReviewOwnerRepo reviewOwnerRepo;

    public void saveMsg(MessageModel msg) {
        messageRepo.save(msg);

    }

    public List<HouseModel> getAllHouses(){
        return houseRepo.findAll();
    }

    public void saveAppointment(AppointmentModel appointment) {

        appointmentRepo.save(appointment);


    }

    public int saveReserveHouse(ReserveModel reserve) {
        // TODO Auto-generated method stub

        List<Coupon> coupons = couponRepo.findAll();

        List<Coupon> coupon = coupons.stream().filter(co -> co.getCouponCode().equals(reserve.getCoupon())).collect(Collectors.toList());

        if(coupon.size() != 0) {
            reserveRepo.save(reserve);

            couponRepo.deleteById(coupon.get(0).getId());
            return 1;
        }
        else {

            return 0;
        }


    }

    public Set<AppointmentModel> getAllAppointmentsByUserId(Long id) {

        List<AppointmentModel> appointments = appointmentRepo.findAll();

        Set<AppointmentModel> appointmentsSet = appointments.stream().filter(apt -> apt.getUserId().equals(id.toString())).collect(Collectors.toSet());
        return appointmentsSet;
    }

    public void savefavourites(FavouritesModel favourite) {
        // TODO Auto-generated method stub
        favouritesRepo.save(favourite);
    }


    public void likeHouse(Long id) {
        // TODO Auto-generated method stub
        HouseModel house = houseRepo.findHouseById(id);
        house.setLikes(house.getLikes() + 1);
        houseRepo.save(house);

    }

    public void disLikeHouse(Long id) {
        // TODO Auto-generated method stub
        HouseModel house = houseRepo.findHouseById(id);
        house.setDislikes(house.getDislikes() + 1);
        houseRepo.save(house);
    }

    public List<HouseModel> searchHouses(String searchKey) {
        // TODO Auto-generated method stub
        List<HouseModel> houses = houseRepo.findAll();
        List<HouseModel> searchedHouses = houses.stream().filter(house -> house.getHouseName().contains(searchKey) ||
                house.getHouseAddress().contains(searchKey) || house.getHouseDetails().contains(searchKey)
                || house.getHouseType().contains(searchKey)).collect(Collectors.toList());
        return searchedHouses;
    }

    public List<MessageModel> findAllMessages(String email) {
        // TODO Auto-generated method stub
        List<MessageModel> msgs = messageRepo.findAll();
        List<MessageModel> ownerMsgs = msgs.stream().filter(msg -> msg.getStudentMail().equals(email) && !msg.getAnswer().equals("")).collect(Collectors.toList());
        return ownerMsgs;
    }

    public List<HouseModel> sort(String price) {
        // TODO Auto-generated method stub
        List<HouseModel> houses = houseRepo.findAll();
        String startprice,endprice;
        int sp,ep;
        startprice=price.split("to")[0];
        System.out.println("-------------"+startprice);
        endprice=price.split("to")[1];
        sp= Integer.parseInt(startprice);
        ep= Integer.parseInt(endprice);
        List<HouseModel> advnaceFilteredHouses = houses.stream().filter(
                house -> Integer.parseInt(house.getHouseRent()) >= sp && Integer.parseInt(house.getHouseRent()) <= ep).collect(Collectors.toList());

        Comparator<HouseModel> rentComparator =
                (house1, house2) -> house1.getHouseRent().compareTo(house2.getHouseRent());
        return advnaceFilteredHouses.stream().sorted(rentComparator).collect(Collectors.toList());
//		return advnaceFilteredHouses;
    }

    public List<HouseModel> filterHouses(String city, String moveInDate) {
        // TODO Auto-generated method stub
        List<HouseModel> houses = houseRepo.findAll();
        List<HouseModel> filteredHouses = houses.stream().filter(house -> house.getCity().equals(city) && house.getAvailableFrom().equals(moveInDate)).collect(Collectors.toList());
        return filteredHouses;
    }

    public List<HouseModel> advanceFilterHouses(String city, String moveInDate, String parking, String petFriendly,
                                                String lawn, String houseType) {
        // TODO Auto-generated method stub
        List<HouseModel> houses = houseRepo.findAll();
        List<HouseModel> advnaceFilteredHouses = houses.stream().filter(house -> house.getCity().equals(city)
                && house.getAvailableFrom().equals(moveInDate) && house.getParking().equals(parking)
                && house.getPetFriendly().equals(petFriendly)
                && house.getLawn().equals(lawn) && house.getHouseType().equals(houseType)).collect(Collectors.toList());
        return advnaceFilteredHouses;
    }

    public void saveReviewOwner(ReviewOwnerModel review) {

        reviewOwnerRepo.save(review);
    }

}
