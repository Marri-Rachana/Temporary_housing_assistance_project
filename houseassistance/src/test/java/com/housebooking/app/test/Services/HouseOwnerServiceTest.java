package com.housebooking.app.test.Services;

import com.housebooking.app.dao.*;
import com.housebooking.app.model.*;
import com.housebooking.app.service.HouseOwnerService;
import com.housebooking.app.service.UserService;
import com.housebooking.app.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HouseOwnerServiceTest {

	@InjectMocks
    private HouseOwnerService houseOwnerService;
	@Mock
	private HouseRepo houseRepo;

	@Mock
	private ReportRepo reportRepo;

	@Mock
	private HomeRepo homeRepo;

	@Mock
	private ReviewOwnerRepo reviewOwnerRepo;

	@Mock
	private ReviewPropertyRepo reviewPropertyRepo;

	@Mock
	private HouseDetailsRepo houseDetailsRepo;
	@Mock
	private HouseAttributesRepo houseAttributesRepo;
	@Mock
	private HouseDocumentRepo houseDocumentRepo;
	@Mock
	private HousePropertiesRepo housePropertiesRepo;
	@Mock
	private HouseLikeOrDislikeRepo houseLikeOrDislikeRepo;
	@Mock
	private HouseStatusRepo houseStatusRepo;
	@Mock
	private UserProfileRepo userProfileRepo;

	@Mock
	private AddressRepo addressRepo;

	@Mock
	private FavouritesRepo favouritesRepo;

	@Mock
	private MessageRepo messageRepo;

	@Test
	public void getHouseTest() {
		List<HouseModel> houseModels = new ArrayList<>();
		houseModels.add(TestUtils.getHouseModel());
		when(houseRepo.findAll()).thenReturn(houseModels);
		HouseModel result = houseOwnerService.getHouse();
		assertEquals(TestUtils.getHouseModel(),result);

	}

	@Test
	public void getHouseTest_NegativeTest() {
		when(houseRepo.findAll()).thenThrow(NullPointerException.class);
		HouseModel result = houseOwnerService.getHouse();
		assertEquals(null,result);
	}


    @Test
	public void getAllHousesByEmailTest() {
		List<HouseModel> houseModels = new ArrayList<>();
		houseModels.add(TestUtils.getHouseModel());
		when(houseRepo.findAllByEmailId(any())).thenReturn(houseModels);
		List<HouseModel> result = houseOwnerService.getAllHousesByEmail("rachana.marri@gmail.com");
		assertEquals(houseModels,result);
	}

	@Test
	public void getAllHousesByEmailTest_NegativeTest() {
		when(houseRepo.findAllByEmailId("rachana.marri@gmail.com")).thenThrow(NullPointerException.class);
		List<HouseModel> result = houseOwnerService.getAllHousesByEmail("rachana.marri@gmail.com");
		assertEquals(null,result);
	}

	@Test
	public void deleteHouseTest()
	{
		List<FavouritesModel> fav = new ArrayList<>();
		fav.add(TestUtils.getFavouritesModel());
		when(addressRepo.findHouseAddress(any())).thenReturn(TestUtils.getAddressModel());
		when(favouritesRepo.findAll()).thenReturn(fav);
		int result =houseOwnerService.deleteHouse(123l);
		assertEquals(1,result);
	}

	@Test
	public void deleteHouse_negativeTest()
	{
		when(addressRepo.findHouseAddress(any())).thenReturn(TestUtils.getAddressModel());
		when(favouritesRepo.findAll()).thenThrow(NullPointerException.class);
		int result =houseOwnerService.deleteHouse(123l);
		assertEquals(0,result,"Can't Delete house");
	}

	
    @Test
	public void getHouseByIdTest() {
		when(houseRepo.findHouseById(any())).thenReturn(TestUtils.getHouseModel());
		HouseModel result = houseOwnerService.getHouseById(123l);
		assertEquals(TestUtils.getHouseModel(),result);
	}

	@Test
	public void getHouseById_negativeTest() {
		when(houseRepo.findHouseById(any())).thenThrow(NullPointerException.class);
		HouseModel result = houseOwnerService.getHouseById(123l);
		assertEquals(null,result);
	}

	@Test
	public void saveReportTest() {
		when(reportRepo.save(any())).thenReturn(TestUtils.getReportModel());
		int result= houseOwnerService.saveReport(TestUtils.getReportModel());
		assertEquals(1,result);
	}

	@Test
	public void saveReportTest_NegativeTest() {
		when(reportRepo.save(any())).thenThrow(NullPointerException.class);
		int result= houseOwnerService.saveReport(TestUtils.getReportModel());
		assertEquals(0,result);
	}

	@Test
	public void getMsgByIdTest(){
		when(messageRepo.findMessageById(any())).thenReturn(TestUtils.getMessageModel());
		MessageModel result = houseOwnerService.getMsgById(123l);
		assertEquals(TestUtils.getMessageModel(),result);
	}

	@Test
	public void getMsgById_NegativeTest(){
		when(messageRepo.findMessageById(any())).thenThrow(NullPointerException.class);
		MessageModel result = houseOwnerService.getMsgById(123l);
		assertEquals(null,result);
	}

	@Test
	public void getAllHousesDetailsByEmailTest(){
		List<HouseModel> houseModel = new ArrayList<>();
		houseModel.add(TestUtils.getHouseModel());
		List<HouseDetailsModel> houseDetailsModel = new ArrayList<>();
		houseDetailsModel.add(TestUtils.getHouseDetailsModel());
		when(houseRepo.findAllByEmailId(any())).thenReturn(houseModel);
		when(houseDetailsRepo.findHouseDetails(any())).thenReturn(TestUtils.getHouseDetailsModel());
		List<HouseDetailsModel> result = houseOwnerService.getAllHousesDetailsByEmail(TestUtils.getHouseModel().getHouseOwnerMail());
		assertEquals(houseDetailsModel,result);
	}

	@Test
	public void getAllHousesDetailsByEmail_NegativeTest(){
		List<HouseModel> houseModel = new ArrayList<>();
		houseModel.add(TestUtils.getHouseModel());
		List<HouseDetailsModel> houseDetailsModel = new ArrayList<>();
		houseDetailsModel.add(TestUtils.getHouseDetailsModel());
		when(houseRepo.findAllByEmailId(any())).thenReturn(houseModel);
		when(houseDetailsRepo.findHouseDetails(any())).thenThrow(NullPointerException.class);
		List<HouseDetailsModel> result = houseOwnerService.getAllHousesDetailsByEmail(TestUtils.getHouseModel().getHouseOwnerMail());
		assertEquals(null,result);
	}

	@Test
	public void getHouseDetailsByIdTest(){
		when(houseDetailsRepo.findHouseDetails(any())).thenReturn(TestUtils.getHouseDetailsModel());
		HouseDetailsModel result = houseOwnerService.getHouseDetailsById(123l);
		assertEquals(TestUtils.getHouseDetailsModel(),result);
	}

	@Test
	public void getHouseDetailsById_negativeTest(){
		when(houseDetailsRepo.findHouseDetails(any())).thenThrow(NullPointerException.class);
		HouseDetailsModel result = houseOwnerService.getHouseDetailsById(123l);
		assertEquals(null,result);
	}

	@Test
	public void getHouseAttributesTest(){
		when(houseAttributesRepo.findHouseAttributes(any())).thenReturn(TestUtils.getHouseAttributesModel());
		HouseAttributesModel result = houseOwnerService.getHouseAttributes(123l);
		assertEquals(TestUtils.getHouseAttributesModel(),result);
	}
	@Test
	public void getHouseAttributes_NegativeTest(){
		when(houseAttributesRepo.findHouseAttributes(any())).thenThrow(NullPointerException.class);
		HouseAttributesModel result = houseOwnerService.getHouseAttributes(123l);
		assertEquals(null,result);
	}

	@Test
	public void getHouseAddressTest(){
		when(addressRepo.findHouseAddress(any())).thenReturn(TestUtils.getAddressModel());
		AddressModel result = houseOwnerService.getHouseAddress(123l);
		assertEquals(TestUtils.getAddressModel(),result);
	}

	@Test
	public void getHouseAddress_negativeTest(){
		when(addressRepo.findHouseAddress(any())).thenThrow(NullPointerException.class);
		AddressModel result = houseOwnerService.getHouseAddress(123l);
		assertEquals(null,result);
	}

	@Test
	public void getAllMyReviewsTest()
	{
		List<ReviewOwnerModel> reviewOwnerModels = new ArrayList<>();
		reviewOwnerModels.add(TestUtils.getReviewOwnerModel());
		when(reviewOwnerRepo.findMyAllReviews(any())).thenReturn(reviewOwnerModels);
		List<ReviewOwnerModel> result = houseOwnerService.getAllMyReviews("rachana.marri@gmail.com");
		assertEquals(reviewOwnerModels,result);

	}

	@Test
	public void getAllMyReviews_negativeTest()
	{
		when(reviewOwnerRepo.findMyAllReviews(any())).thenThrow(NullPointerException.class);
		List<ReviewOwnerModel> result = houseOwnerService.getAllMyReviews("rachana.marri@gmail.com");
		assertEquals(null,result);
	}

	@Test
	public void getAllPropertyReviewsTest()
	{
		List<ReviewPropertyModel> reviews = new  ArrayList<ReviewPropertyModel>();
		reviews.add(TestUtils.getReviewPropertyModel());
		List<HouseModel> houseModels = new ArrayList<>();
		houseModels.add(TestUtils.getHouseModel());
		when(reviewPropertyRepo.findAll()).thenReturn((reviews));
		when(houseRepo.findAll()).thenReturn(houseModels);
		List<ReviewPropertyModel> results = houseOwnerService.getAllPropertyReviews("rachana.marri@gmail.com");
		assertEquals(reviews,results);
	}

	@Test
	public void getAllPropertyReviewsTest_negativeTest()
	{
		List<ReviewPropertyModel> reviews = new  ArrayList<ReviewPropertyModel>();
		reviews.add(TestUtils.getReviewPropertyModel());
		List<HouseModel> houseModels = new ArrayList<>();
		houseModels.add(TestUtils.getHouseModel());
		when(reviewPropertyRepo.findAll()).thenThrow(NullPointerException.class);
		List<ReviewPropertyModel> results = houseOwnerService.getAllPropertyReviews("rachana123.marri@gmail.com");
		assertEquals(null,results);
	}

	@Test
	public void saveHouseTest()
	{
		MultipartFile multipartFileImg = new MockMultipartFile("Test",TestUtils.getHouseDocumentModel().getDocument());
		MultipartFile multipartFileDoc = new MockMultipartFile("Test",TestUtils.getHouseDocumentModel().getDocument());
		when(houseRepo.save(any())).thenReturn(TestUtils.getHouseModel());
		when(houseDetailsRepo.save(any())).thenReturn(TestUtils.getHouseDetailsModel());
		when(houseAttributesRepo.save(any())).thenReturn(TestUtils.getHouseAttributesModel());
		when(houseDocumentRepo.save(any())).thenReturn(TestUtils.getHouseDocumentModel());
		when(housePropertiesRepo.save(any())).thenReturn(TestUtils.getHousePropertiesModel());
		when(houseLikeOrDislikeRepo.save(any())).thenReturn(TestUtils.getHouseLikeOrDislikeModel());
		when(houseStatusRepo.save(any())).thenReturn(TestUtils.getHouseStatusModel());
		when(userProfileRepo.findUserProfile(any())).thenReturn(TestUtils.getUserProfileModel());
		when(addressRepo.save(any())).thenReturn(TestUtils.getAddressModel());
		when(homeRepo.findbyEmail(any())).thenReturn(TestUtils.getUserModel());
		houseOwnerService.saveHouse(TestUtils.getHouseModel(), "rachana.marri@gmail.com",multipartFileImg,multipartFileDoc, "test",
				"2000", "8767898765", "4",  "2","18",
				"Mulberry", "Normal", "61761", "Yes","No","Yes", "01-12-2022");
		verify(houseRepo,times(1)).save(any());
		verify(addressRepo,times(1)).save(any());
	}

	@Test
	public void updateHouse_test()
	{
		MultipartFile multipartFileImg = new MockMultipartFile("Test",TestUtils.getHouseDocumentModel().getDocument());
		MultipartFile multipartFileDoc = new MockMultipartFile("Test",TestUtils.getHouseDocumentModel().getDocument());
		when(houseRepo.save(any())).thenReturn(TestUtils.getHouseModel());
		when(houseDetailsRepo.findHouseDetails(any())).thenReturn(TestUtils.getHouseDetailsModel());
		when(houseDetailsRepo.save(any())).thenReturn(TestUtils.getHouseDetailsModel());
		when(houseAttributesRepo.findHouseAttributes(any())).thenReturn(TestUtils.getHouseAttributesModel());
		when(houseAttributesRepo.save(any())).thenReturn(TestUtils.getHouseAttributesModel());
		when(houseDocumentRepo.save(any())).thenReturn(TestUtils.getHouseDocumentModel());
		when(houseDocumentRepo.findHouseDocument(any())).thenReturn(TestUtils.getHouseDocumentModel());
		when(houseDocumentRepo.save(any())).thenReturn(TestUtils.getHouseDocumentModel());
		when(housePropertiesRepo.findHouseProperties(any())).thenReturn(TestUtils.getHousePropertiesModel());
		when(housePropertiesRepo.save(any())).thenReturn(TestUtils.getHousePropertiesModel());
		when(houseStatusRepo.findHouseStatus(any())).thenReturn(TestUtils.getHouseStatusModel());
		when(addressRepo.findHouseAddress(any())).thenReturn(TestUtils.getAddressModel());
		when(addressRepo.save(any())).thenReturn(TestUtils.getAddressModel());
		houseOwnerService.updateHouse(TestUtils.getHouseModel(), "rachana.marri@gmail.com",multipartFileImg,multipartFileDoc, "test",
				"2000", "8767898765", "4",  "2","18",
				"Mulberry", "Normal", "61761", "Yes","No","Yes", "1","1","01-12-2022");
		verify(houseRepo,times(1)).save(any());
		verify(addressRepo,times(1)).save(any());

	}

	@Test
	public void findAllMessages()
	{
		List<MessageModel> messageModels = new ArrayList<>();
		when(messageRepo.findAll()).thenReturn(messageModels);
		List<MessageModel> results = houseOwnerService.findAllMessages("rachana.marri@gmail.com");
		assertEquals(messageModels,results);

	}

	@Test
	public void findAllMessages_negativeTest()
	{
		List<MessageModel> messageModels = new ArrayList<>();
		when(messageRepo.findAll()).thenThrow(NullPointerException.class);
		List<MessageModel> results = houseOwnerService.findAllMessages("rachana.marri@gmail.com");
		assertEquals(null,results);

	}




}
