package com.housebooking.app.test.Services;

import com.housebooking.app.dao.*;
import com.housebooking.app.model.*;
import com.housebooking.app.service.UserService;
import com.housebooking.app.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    private HouseRepo houseRepo;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private FavouritesRepo favouritesRepo;

    @Mock
    private AppointmentRepo appointmentRepo;

    @Mock
    private ReviewOwnerRepo reviewOwnerRepo;

    @Mock
    private HouseDetailsRepo houseDetailsRepo;

    @Mock
    private HouseAttributesRepo houseAttributesRepo;

    @Mock
    private HouseLikeOrDislikeRepo houseLikeOrDislikeRepo;

    @Mock
    private HousePropertiesRepo housePropertiesRepo;

    @Mock
    private HouseStatusRepo houseStatusRepo;

    @Mock
    private LikesModelRepo likesModelRepo;

    @Mock
    private DislikesModelRepo dislikesModelRepo;

    @Mock
    private ReviewPropertyRepo reviewPropertyRepo;

    @Mock
    private MessageRepo messageRepo;

    @Mock
    private AddressRepo addressRepo;

    @Mock
    private CouponRepo couponRepo;

    @Test
    public void getAllHouseDetailsTest(){
        List<HouseStatusModel> houseStatusModels = new ArrayList<>();
        houseStatusModels.add(TestUtils.getHouseStatusModel());
        when(houseStatusRepo.findAll()).thenReturn(houseStatusModels);
        when(houseDetailsRepo.findHouseDetails(any())).thenReturn(TestUtils.getHouseDetailsModel());
        List<HouseDetailsModel> allHouseDetails = userService.getAllHouseDetails();
        assertEquals(1,allHouseDetails.size(),"allHouseDetails should be 1");
        assertEquals("MyHome",allHouseDetails.get(0).getHouseName(),"House name should be MyHome");
    }

    @Test
    public void getAllHouseDetails_negativeTest(){
        when(houseStatusRepo.findAll()).thenThrow(NullPointerException.class);
        List<HouseDetailsModel> allHouseDetails = userService.getAllHouseDetails();
        assertEquals(null,allHouseDetails);
    }

    @Test
    public void saveBookHouseTest(){
        BookModel bookModel = TestUtils.getBookModel();
        when(housePropertiesRepo.findHouseProperties(any())).thenReturn(TestUtils.getHousePropertiesModel());
        when(houseStatusRepo.findHouseStatus(any())).thenReturn(TestUtils.getHouseStatusModel());
        when(bookRepo.save(any())).thenReturn(bookModel);
        bookModel.setCoupon("");
        BookModel saveBookHouse = userService.saveBookHouse(bookModel);
        assertEquals("123",saveBookHouse.getHouseId(),"HouseId should be 123");
        List<Coupon> coupon = new ArrayList<>();
        coupon.add(TestUtils.getCoupon());
        when(couponRepo.findAll()).thenReturn(coupon);
        when(housePropertiesRepo.findHouseProperties(any())).thenReturn(TestUtils.getHousePropertiesModel());
        when(houseStatusRepo.findHouseStatus(any())).thenReturn(TestUtils.getHouseStatusModel());
        when(bookRepo.save(any())).thenReturn(bookModel);
        bookModel.setCoupon("123");
        saveBookHouse = userService.saveBookHouse(bookModel);
        assertEquals("123",saveBookHouse.getHouseId(),"HouseId should be 123");
        bookModel.setCoupon("321");
        BookModel bookHouse = userService.saveBookHouse(bookModel);
        assertNull(bookHouse);
    }

    @Test
    public void savefavouritesTest(){
        when(favouritesRepo.save(any())).thenReturn(TestUtils.getFavouritesModel());
        int result = userService.savefavourites(TestUtils.getFavouritesModel());
        assertEquals(1,result);
    }

    @Test
    public void savefavourites_negativeTest(){
        when(favouritesRepo.save(any())).thenThrow(NullPointerException.class);
        int result = userService.savefavourites(TestUtils.getFavouritesModel());
        assertEquals(0,result);
    }

    @Test
    public void likeHouseTest(){
        List<DislikesModel> dislikesModels = new ArrayList<>();
        dislikesModels.add(TestUtils.getDislikesModel());
        when(houseRepo.findHouseById(any())).thenReturn(TestUtils.getHouseModel());
        when(houseLikeOrDislikeRepo.findLikeOrDislikeById(any())).thenReturn(TestUtils.getHouseLikeOrDislikeModel());
        when(dislikesModelRepo.findAll()).thenReturn(dislikesModels);
        userService.likeHouse(123L,321L);
        verify(dislikesModelRepo,times(1)).delete(any());
    }

    @Test
    public void disLikeHouseTest(){
        List<LikesModel> likesModels = new ArrayList<>();
        likesModels.add(TestUtils.getLikesModel());
        when(houseRepo.findHouseById(any())).thenReturn(TestUtils.getHouseModel());
        when(houseLikeOrDislikeRepo.findLikeOrDislikeById(any())).thenReturn(TestUtils.getHouseLikeOrDislikeModel());
        when(likesModelRepo.findAll()).thenReturn(likesModels);
        userService.disLikeHouse(123L,321L);
        verify(likesModelRepo,times(1)).delete(any());
    }

    @Test
    public void saveAppointmentTest(){
        when(appointmentRepo.save(any())).thenReturn(TestUtils.getAppointmentModel());
        userService.saveAppointment(TestUtils.getAppointmentModel());
        verify(appointmentRepo,times(1)).save(any());
    }

    @Test
    public void saveAppointment_negativeTest(){
        when(appointmentRepo.save(any())).thenThrow(NullPointerException.class);
        int result = userService.saveAppointment(TestUtils.getAppointmentModel());
        assertEquals(0,result);
    }

    @Test
    public void getAllAppointmentsByUserIdTest(){
        List<AppointmentModel> appointmentModels = new ArrayList<>();
        appointmentModels.add(TestUtils.getAppointmentModel());
        List<HouseModel> houseModels = new ArrayList<>();
        houseModels.add(TestUtils.getHouseModel());
        when(appointmentRepo.findAll()).thenReturn(appointmentModels);
        when(houseRepo.findAll()).thenReturn(houseModels);
        List<AppointmentModel> allAppointmentsByUserId = userService.getAllAppointmentsByUserId("rachana.marri@gmail.com");
        assertEquals(1,allAppointmentsByUserId.size(),"size should be 1");
        assertEquals("123",allAppointmentsByUserId.get(0).getHouseId(),"HouseId should be 123");
    }

    @Test
    public void getAllAppointmentsByUserId_negativeTest(){
        List<AppointmentModel> appointmentModels = new ArrayList<>();
        appointmentModels.add(TestUtils.getAppointmentModel());
        List<HouseModel> houseModels = new ArrayList<>();
        houseModels.add(TestUtils.getHouseModel());
        when(appointmentRepo.findAll()).thenThrow(NullPointerException.class);
        List<AppointmentModel> allAppointmentsByUserId = userService.getAllAppointmentsByUserId("rachana.marri@gmail.com");
        assertEquals(null,allAppointmentsByUserId);
    }

    @Test
    public void saveReviewOwnerTest(){
        when(reviewOwnerRepo.save(any())).thenReturn(TestUtils.getReviewOwnerModel());
        userService.saveReviewOwner(TestUtils.getReviewOwnerModel());
        verify(reviewOwnerRepo,times(1)).save(any());
    }

    @Test
    public void saveReviewOwner_negativeTest(){
        when(reviewOwnerRepo.save(any())).thenReturn(NullPointerException.class);
        userService.saveReviewOwner(TestUtils.getReviewOwnerModel());
        verify(reviewOwnerRepo,times(1)).save(any());
    }

    @Test
    public void saveMsgTest(){
        when(messageRepo.save(any())).thenReturn(TestUtils.getMessageModel());
        userService.saveMsg(TestUtils.getMessageModel());
        verify(messageRepo,times(1)).save(any());
    }

    @Test
    public void saveMsg_negativeTest(){
        when(messageRepo.save(any())).thenReturn(NullPointerException.class);
        int result = userService.saveMsg(TestUtils.getMessageModel());
        assertEquals(0,result);
    }

    @Test
    public void findAllMessagesTest(){
        List<MessageModel> messageModels = new ArrayList<>();
        messageModels.add(TestUtils.getMessageModel());
        when(messageRepo.findAll()).thenReturn(messageModels);
        List<MessageModel> allMessages = userService.findAllMessages("student@gmail.com");
        assertEquals(1,allMessages.size(),"allMessages size should be 1");
    }

    @Test
    public void findAllMessages_negativeTest(){
        when(messageRepo.findAll()).thenThrow(NullPointerException.class);
        List<MessageModel> allMessages = userService.findAllMessages("student@gmail.com");
        assertEquals(null,allMessages);
    }
    @Test
    public void searchHousesTest(){
        List<HouseDetailsModel> houseDetailsModelList = new ArrayList<>();
        houseDetailsModelList.add(TestUtils.getHouseDetailsModel());
        when(houseDetailsRepo.findAll()).thenReturn(houseDetailsModelList);
        List<HouseDetailsModel> houseDetailsModel = userService.searchHouses("MyHome");
        assertEquals(1,houseDetailsModel.size(),"houseDetailsModelList size should be 1");
    }

    @Test
    public void searchHouses_negativeTest(){
        when(houseDetailsRepo.findAll()).thenThrow(NullPointerException.class);
        List<HouseDetailsModel> houseDetailsModel = userService.searchHouses("MyHome");
        assertEquals(null,houseDetailsModel);
    }

    @Test
    public void filterHousesTest(){
        List<HouseDetailsModel> houseDetailsModel = new ArrayList<>();
        houseDetailsModel.add(TestUtils.getHouseDetailsModel());
        List<AddressModel> addressModels = new ArrayList<>();
        addressModels.add(TestUtils.getAddressModel());
        when(houseDetailsRepo.findAll()).thenReturn(houseDetailsModel);
        when(addressRepo.findAll()).thenReturn(addressModels);
        List<HouseDetailsModel> houseDetailsModelList = userService.filterHouses("Hyd", "12-31-2022");
        assertEquals(1,houseDetailsModelList.size(),"houseDetailsModelList size should be 1");
    }

    @Test
    public void filterHouses_negativeTest(){
        List<HouseDetailsModel> houseDetailsModel = new ArrayList<>();
        houseDetailsModel.add(TestUtils.getHouseDetailsModel());
        List<AddressModel> addressModels = new ArrayList<>();
        addressModels.add(TestUtils.getAddressModel());
        when(houseDetailsRepo.findAll()).thenThrow(NullPointerException.class);
        List<HouseDetailsModel> houseDetailsModelList = userService.filterHouses("Hyd", "12-31-2022");
        assertEquals(null,houseDetailsModelList);
    }

    @Test
    public void advanceFilterHousesTest(){
        List<HouseModel> houseModels = new ArrayList<>();
        houseModels.add(TestUtils.getHouseModel());
        List<HouseDetailsModel> houseDetailsModelsList = new ArrayList<>();
        houseDetailsModelsList.add(TestUtils.getHouseDetailsModel());
        List<AddressModel> addressModels = new ArrayList<>();
        addressModels.add(TestUtils.getAddressModel());
        List<HouseAttributesModel> houseAttributesModelsModels = new ArrayList<>();
        houseAttributesModelsModels.add(TestUtils.getHouseDetailsModel().getHouseAttributes());
        when(houseRepo.findAll()).thenReturn(houseModels);
        when(houseDetailsRepo.findHouseDetails(any())).thenReturn(TestUtils.getHouseDetailsModel());
        when(addressRepo.findAll()).thenReturn(addressModels);
        when(houseDetailsRepo.findAll()).thenReturn(houseDetailsModelsList);
        when(houseAttributesRepo.findAll()).thenReturn(houseAttributesModelsModels);
        List<HouseDetailsModel> houseDetailsModelList = userService.advanceFilterHouses("Hyd", "12-32-2022", "Yes", "Yes", "Yes", "Rented");
        assertEquals(1,houseDetailsModelList.size(),"houseDetailsModelList should be 1");
    }

    @Test
    public void advanceFilterHouses_negativeTest(){
        List<HouseModel> houseModels = new ArrayList<>();
        houseModels.add(TestUtils.getHouseModel());
        List<HouseDetailsModel> houseDetailsModelsList = new ArrayList<>();
        houseDetailsModelsList.add(TestUtils.getHouseDetailsModel());
        List<AddressModel> addressModels = new ArrayList<>();
        addressModels.add(TestUtils.getAddressModel());
        List<HouseAttributesModel> houseAttributesModelsModels = new ArrayList<>();
        houseAttributesModelsModels.add(TestUtils.getHouseDetailsModel().getHouseAttributes());
        when(houseRepo.findAll()).thenThrow(NullPointerException.class);
        List<HouseDetailsModel> houseDetailsModelList = userService.advanceFilterHouses("Hyd", "12-32-2022", "Yes", "Yes", "Yes", "Rented");
        assertEquals(null,houseDetailsModelList);
    }

    @Test
    public void sortTest(){
        List<HouseDetailsModel> houseDetailsModelsList = new ArrayList<>();
        houseDetailsModelsList.add(TestUtils.getHouseDetailsModel());
        when(houseDetailsRepo.findAll()).thenReturn(houseDetailsModelsList);
        List<HouseDetailsModel> houseDetailsModelList = userService.sort("10000to100000");
        assertEquals(1,houseDetailsModelList.size(),"houseDetailsModelList size should be 1");
    }

    @Test
    public void reserveHouseTest(){
        when(housePropertiesRepo.findHouseProperties(any())).thenReturn(TestUtils.getHousePropertiesModel());
        when(houseStatusRepo.findHouseStatus(any())).thenReturn(TestUtils.getHouseStatusModel());
        userService.reserveHouse(123L,123L);
        verify(houseStatusRepo,times(1)).save(any());
    }

    @Test
    public void reserveHouse_negativeTest(){
        when(housePropertiesRepo.findHouseProperties(any())).thenThrow(NullPointerException.class);
        int result = userService.reserveHouse(123L,123L);
        assertEquals(0,result);
    }

    @Test
    public void saveReviewPropertyTest(){
        when(reviewPropertyRepo.save(any())).thenReturn(TestUtils.getReviewPropertyModel());
        userService.saveReviewProperty(TestUtils.getReviewPropertyModel());
        verify(reviewPropertyRepo,times(1)).save(any());
    }

    @Test
    public void saveReviewProperty_negativeTest(){
        when(reviewPropertyRepo.save(any())).thenReturn(NullPointerException.class);
        int result = userService.saveReviewProperty(TestUtils.getReviewPropertyModel());
        assertEquals(0,result);
    }

    @Test
    public void findAllFavsTest(){
        List<FavouritesModel> favouritesModelList = new ArrayList<>();
        favouritesModelList.add(TestUtils.getFavouritesModel());
        when(favouritesRepo.findUserFavs(any())).thenReturn(favouritesModelList);
        when(houseDetailsRepo.findHouseDetails(any())).thenReturn(TestUtils.getHouseDetailsModel());
        List<HouseDetailsModel> allFavs = userService.findAllFavs(123L);
        assertEquals(1,allFavs.size(),"allFavs size should be 1");
    }

    @Test
    public void findAllFavs_negativeTest(){
        when(favouritesRepo.findUserFavs(any())).thenThrow(NullPointerException.class);
        List<HouseDetailsModel> allFavs = userService.findAllFavs(123L);
        assertEquals(null,allFavs);
    }

    @Test
    public void getAllBookingsByUserIdTest(){
        List<BookModel> bookModels = new ArrayList<>();
        bookModels.add(TestUtils.getBookModel());
        when(bookRepo.getAllBookingsOfUser(any())).thenReturn(bookModels);
        List<BookModel> allBookingsByUserId = userService.getAllBookingsByUserId(123L);
        assertEquals(1,allBookingsByUserId.size(),"allBookingsByUserId size should be 1");
    }

    @Test
    public void getAllBookingsByUserId_negativeTest(){
        when(bookRepo.getAllBookingsOfUser(any())).thenThrow(NullPointerException.class);
        List<BookModel> allBookingsByUserId = userService.getAllBookingsByUserId(123L);
        assertEquals(null,allBookingsByUserId);
    }

    @Test
    public void getIsLikedByUser_positiveAndNegativeTest(){
        when(likesModelRepo.findIsLikedByUser(any(),any())).thenReturn(TestUtils.getLikesModel());
        int isLikedByUser = userService.getIsLikedByUser(123L, 123L);
        assertEquals(1,isLikedByUser,"isLikedByUser should be 1");

        when(likesModelRepo.findIsLikedByUser(any(),any())).thenReturn(null);
        isLikedByUser = userService.getIsLikedByUser(123L, 123L);
        assertEquals(0,isLikedByUser,"isLikedByUser should be 0");
    }

    @Test
    public void getIsDisLikedByUser_positiveAndNegativeTest(){
        when(dislikesModelRepo.findIsDisLikedByUser(any(),any())).thenReturn(TestUtils.getDislikesModel());
        int isDisLikedByUser = userService.getIsDisLikedByUser(123L, 123L);
        assertEquals(1,isDisLikedByUser,"isDisLikedByUser should be 1");

        when(dislikesModelRepo.findIsDisLikedByUser(any(),any())).thenReturn(null);
        isDisLikedByUser = userService.getIsDisLikedByUser(123L, 123L);
        assertEquals(0,isDisLikedByUser,"isLikedByUser should be 0");
    }
}
