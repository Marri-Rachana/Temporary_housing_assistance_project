package com.housebooking.app.utils;

import com.housebooking.app.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;

public class TestUtils {

    public static HouseStatusModel getHouseStatusModel() {
        HouseStatusModel houseStatusModel = new HouseStatusModel();
        houseStatusModel.setIsBooked("0");
        houseStatusModel.setIsHide("0");
        houseStatusModel.setIsVerified("0");
        return houseStatusModel;
    }

    public static HouseDetailsModel getHouseDetailsModel() {
        HouseDetailsModel houseDetailsModel = new HouseDetailsModel();
        houseDetailsModel.setHouse(getHouseModel());
        houseDetailsModel.setHouseName("MyHome");
        houseDetailsModel.setId(123L);
        houseDetailsModel.setAvailableFrom("12-31-2022");
        houseDetailsModel.setHouseRent("10000");
        houseDetailsModel.setHouseAttributes(getHouseAttributesModel());


        return houseDetailsModel;
    }

    public static HouseDetailsModel getHouseDetailsModel1() {
        HouseDetailsModel houseDetailsModel = new HouseDetailsModel();
        houseDetailsModel.setHouse(getHouseModel());
        houseDetailsModel.setHouseName("MyHome");
        houseDetailsModel.setId(123L);
        houseDetailsModel.setAvailableFrom("12-31-2022");
        return houseDetailsModel;
    }

    public static HouseAttributesModel getHouseAttributesModel() {
        HouseAttributesModel houseAttributesModel = new HouseAttributesModel();
        houseAttributesModel.setParking("Yes");
        houseAttributesModel.setLawn("Yes");
        houseAttributesModel.setId(123L);
        houseAttributesModel.setDetails(getHouseDetailsModel1());
        return houseAttributesModel;
    }

    public static HouseModel getHouseModel() {
        HouseModel houseModel = new HouseModel();
        houseModel.setHouseType("Rented");
        houseModel.setHouseOwnerMail("rachana.marri@gmail.com");
        houseModel.setId(123L);
        return houseModel;
    }

    public static HousePropertiesModel getHousePropertiesModel() {
        HousePropertiesModel housePropertiesModel = new HousePropertiesModel();
        housePropertiesModel.setHouse(getHouseModel());
        housePropertiesModel.setIsBooked("1");
        return housePropertiesModel;
    }

    public static BookModel getBookModel() {
        BookModel bookModel = new BookModel();
        bookModel.setHouseId("123");
        bookModel.setHouseRent("10000");
        return bookModel;
    }

    public static Coupon getCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCouponCode("123");
        coupon.setCouponTitle("HouseCoupon");
        coupon.setDiscountAmount("100");
        return coupon;
    }

    public static FavouritesModel getFavouritesModel() {
        FavouritesModel favouritesModel = new FavouritesModel();
        favouritesModel.setHouseId("123");
        favouritesModel.setUserId("133");
        return favouritesModel;
    }

    public static HouseLikeOrDislikeModel getHouseLikeOrDislikeModel() {
        HouseLikeOrDislikeModel houseLikeOrDislikeModel = new HouseLikeOrDislikeModel();
        houseLikeOrDislikeModel.setLikes(1);
        houseLikeOrDislikeModel.setDislikes(0);
        houseLikeOrDislikeModel.setProperty(getHousePropertiesModel());
        return houseLikeOrDislikeModel;
    }

    public static DislikesModel getDislikesModel() {
        DislikesModel dislikesModel = new DislikesModel();
        dislikesModel.setHouse_id("123");
        dislikesModel.setUser_id("321");
        return dislikesModel;
    }

    public static LikesModel getLikesModel() {
        LikesModel likesModel = new LikesModel();
        likesModel.setHouse_id("123");
        likesModel.setUser_id("321");
        return likesModel;
    }

    public static AppointmentModel getAppointmentModel() {
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setHouseId("123");
        appointmentModel.setUserId("321");
        appointmentModel.setType("rent");
        return appointmentModel;
    }

    public static ReviewOwnerModel getReviewOwnerModel() {
        ReviewOwnerModel reviewOwnerModel = new ReviewOwnerModel();
        reviewOwnerModel.setOwnerMail("rachana.marri@gmail.com");
        reviewOwnerModel.setRating("5");
        reviewOwnerModel.setDescription("nice");
        return reviewOwnerModel;
    }

    public static MessageModel getMessageModel() {
        MessageModel messageModel = new MessageModel();
        messageModel.setOwnerMail("rachana.marri@gmail.com");
        messageModel.setStudentMail("student@gmail.com");
        messageModel.setAnswer("Yes we house for rent");
        return messageModel;
    }

    public static AddressModel getAddressModel() {
        AddressModel addressModel = new AddressModel();
        addressModel.setHouseId(123L);
        addressModel.setCity("Hyd");
        return addressModel;
    }

    public static ReviewPropertyModel getReviewPropertyModel() {
        ReviewPropertyModel reviewPropertyModel = new ReviewPropertyModel();
        reviewPropertyModel.setRating("5");
        reviewPropertyModel.setId(13L);
        reviewPropertyModel.setHouseId("123");
        return reviewPropertyModel;
    }

    public static UserModel getUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUsername("rachana");
        userModel.setEmail("rachana@gmail.com");
        userModel.setPassword("123456");
        return userModel;
    }

    public static UserSecurityModel getUserSecurityModel() {
        UserSecurityModel userSecurityModel = new UserSecurityModel();
        userSecurityModel.setUser(getUserModel());
        userSecurityModel.setSecurityQuestion("Graduation college");
        userSecurityModel.setSecurityAnswer("Gurunanak engineering college");
        return userSecurityModel;
    }

    public static UserProfileModel getUserProfileModel() {
        UserProfileModel userProfileModel = new UserProfileModel();
        userProfileModel.setUser(getUserModel());
        userProfileModel.setAge("22");
        return userProfileModel;
    }

    public static Announcement getAnnouncement() {
        Announcement announcement = new Announcement();
        announcement.setAnnouncementTitle("announce");
        return announcement;
    }


    public static ReviewModel getReviewModel() {
        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setRating("5");
        return reviewModel;
    }

    public static ReportModel getReportModel() {
        ReportModel reportModel = new ReportModel();
        reportModel.setUserMail("test@test.test");
        return reportModel;
    }

    public static HouseDocumentModel getHouseDocumentModel() {
        byte[] test = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
        HouseDocumentModel houseDocumentModel = new HouseDocumentModel();
        houseDocumentModel.setId(1l);
        houseDocumentModel.setDocument(test);
        houseDocumentModel.setHouse(houseDocumentModel.getHouse());
        return houseDocumentModel;
    }

}
