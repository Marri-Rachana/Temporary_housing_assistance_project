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




}
