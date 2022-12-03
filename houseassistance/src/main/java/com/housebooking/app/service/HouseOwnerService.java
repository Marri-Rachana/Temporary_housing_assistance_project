package com.housebooking.app.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.housebooking.app.dao.AddressRepo;
import com.housebooking.app.dao.FavouritesRepo;
import com.housebooking.app.dao.HomeRepo;
import com.housebooking.app.dao.HouseAttributesRepo;
import com.housebooking.app.dao.HouseDetailsRepo;
import com.housebooking.app.dao.HouseDocumentRepo;
import com.housebooking.app.dao.HouseLikeOrDislikeRepo;
import com.housebooking.app.dao.HousePropertiesRepo;
import com.housebooking.app.dao.HouseRepo;
import com.housebooking.app.dao.HouseStatusRepo;
import com.housebooking.app.dao.MessageRepo;
import com.housebooking.app.dao.ReportRepo;
import com.housebooking.app.dao.ReviewOwnerRepo;
import com.housebooking.app.dao.ReviewPropertyRepo;
import com.housebooking.app.dao.ReviewRepo;
import com.housebooking.app.dao.TicketRepo;
import com.housebooking.app.dao.UserProfileRepo;
import com.housebooking.app.model.AddressModel;
import com.housebooking.app.model.FavouritesModel;
import com.housebooking.app.model.HouseAttributesModel;
import com.housebooking.app.model.HouseDetailsModel;
import com.housebooking.app.model.HouseDocumentModel;
import com.housebooking.app.model.HouseLikeOrDislikeModel;
import com.housebooking.app.model.HouseModel;
import com.housebooking.app.model.HousePropertiesModel;
import com.housebooking.app.model.HouseStatusModel;
import com.housebooking.app.model.MessageModel;
import com.housebooking.app.model.ReportModel;
import com.housebooking.app.model.ReviewModel;
import com.housebooking.app.model.ReviewOwnerModel;
import com.housebooking.app.model.ReviewPropertyModel;
import com.housebooking.app.model.TicketModel;
import com.housebooking.app.model.UserProfileModel;

@Service
public class HouseOwnerService {

	@Autowired
	private HouseRepo houseRepo;

	@Autowired
	private ReportRepo reportRepo;

	@Autowired
	private HomeRepo homeRepo;

	@Autowired
	private ReviewOwnerRepo reviewOwnerRepo;

	@Autowired
	private ReviewPropertyRepo reviewPropertyRepo;

	@Autowired
	private HouseDetailsRepo houseDetailsRepo;
	@Autowired
	private HouseAttributesRepo houseAttributesRepo;
	@Autowired
	private HouseDocumentRepo houseDocumentRepo;
	@Autowired
	private HousePropertiesRepo housePropertiesRepo;
	@Autowired
	private HouseLikeOrDislikeRepo houseLikeOrDislikeRepo;
	@Autowired
	private HouseStatusRepo houseStatusRepo;
	@Autowired
	private UserProfileRepo userProfileRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private FavouritesRepo favouritesRepo;

	@Autowired
	private MessageRepo messageRepo;

	public void saveHouse(HouseModel house, String houseOwneremail, MultipartFile houseImage, MultipartFile doc, String houseName,
			String houseRent, String houseContact, String noOfBedrooms,  String noOfBathrooms,String doorNo,
			String street, String city, String zipCode, String parking,String petFriendly,String lawn, String availableFrom) {


		house.setHouseOwnerMail(houseOwneremail);

		HouseModel savedHouse = houseRepo.save(house);

		HouseDetailsModel houseDetails = new HouseDetailsModel();

		houseDetails.setHouse(savedHouse);
		houseDetails.setHouseName(houseName);
		houseDetails.setHouseRent(houseRent);
		houseDetails.setHouseContact(houseContact);
		houseDetails.setNoOfBedrooms(noOfBedrooms);
		houseDetails.setNoOfBathrooms(noOfBathrooms);
		houseDetails.setAvailableFrom(availableFrom);
		try {
			houseDetails.setHousePhoto(Base64.getEncoder().encodeToString(houseImage.getBytes()));
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		HouseDetailsModel savedHouseDetails = houseDetailsRepo.save(houseDetails);


		HouseAttributesModel houseAttributes = new HouseAttributesModel();

		houseAttributes.setDetails(savedHouseDetails);
		houseAttributes.setLawn(lawn);
		houseAttributes.setNoOfBedrooms(noOfBedrooms);
		houseAttributes.setParking(parking);
		houseAttributes.setPetFriendly(petFriendly);

		houseAttributesRepo.save(houseAttributes);

		HouseDocumentModel houseDocument = new HouseDocumentModel();

		houseDocument.setHouse(savedHouse);
		try {
//			houseDocument.setDocument(Base64.getEncoder().encodeToString(doc.getBytes()));
			houseDocument.setDocument(doc.getBytes());
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		houseDocumentRepo.save(houseDocument);


		HousePropertiesModel houseProperty = new HousePropertiesModel();

		houseProperty.setHouse(savedHouse);
		houseProperty.setIsBooked("0");
		houseProperty.setIsHide("0");
		houseProperty.setIsVerified("0");
		HousePropertiesModel savedHouseProperty = housePropertiesRepo.save(houseProperty);


		HouseLikeOrDislikeModel houseLikeOrDislike = new HouseLikeOrDislikeModel();

		houseLikeOrDislike.setDislikes(0);
		houseLikeOrDislike.setLikes(0);
		houseLikeOrDislike.setProperty(savedHouseProperty);

		houseLikeOrDislikeRepo.save(houseLikeOrDislike);

		HouseStatusModel houseStatus = new HouseStatusModel();

		houseStatus.setIsBooked("0");
		houseStatus.setIsHide("0");
		houseStatus.setIsVerified("0");
		houseStatus.setProperty(savedHouseProperty);

		houseStatusRepo.save(houseStatus);

		AddressModel address = new AddressModel();


		 UserProfileModel userProfile = userProfileRepo.findUserProfile(homeRepo.findbyEmail(houseOwneremail).getId());

		address.setProfileId(userProfile.getId());
		address.setCity(city);
		address.setDoorNo(doorNo);
		address.setStreet(street);
		address.setZipCode(zipCode);
		address.setHouseId(savedHouse.getId());


		addressRepo.save(address);

	}

	public HouseModel getHouse() {
		// TODO Auto-generated method stub
		return houseRepo.findAll().get(0);
	}

	public List<HouseModel> getAllHousesByEmail(String emailId) {
		// TODO Auto-generated method stub
		List<HouseModel> houses = houseRepo.findAllByEmailId(emailId);

//		houses.forEach(house -> house.setHouseImage(Base64.getEncoder().encodeToString(house.getHousePhoto())));
		return houses;
	}

	public void deleteHouse(Long id) {
		// TODO Auto-generated method stub
		AddressModel address = addressRepo.findHouseAddress(id);
		addressRepo.deleteById(address.getId());

		houseStatusRepo.deleteById(id);
		houseLikeOrDislikeRepo.deleteById(id);
		houseAttributesRepo.deleteById(id);
		housePropertiesRepo.deleteById(id);
		houseDetailsRepo.deleteById(id);
		houseDocumentRepo.deleteById(id);
		houseRepo.deleteById(id);

		List<FavouritesModel> favourites = favouritesRepo.findAll();

		List<FavouritesModel> favos = new ArrayList<FavouritesModel>();

		favourites.forEach(fav -> {
			if(fav.getHouseId().equals(id.toString())) {
				favos.add(fav);
			}
		});

		favouritesRepo.deleteAll(favos);



	}

	public HouseModel getHouseById(Long id) {
		// TODO Auto-generated method stub

		HouseModel house = houseRepo.findHouseById(id);
//		System.out.println(house.getHouseAddress());
		return house;
	}

	public void saveReport(ReportModel report) {
		// TODO Auto-generated method stub
		reportRepo.save(report);
	}



	public List<MessageModel> findAllMessages(String email) {
		// TODO Auto-generated method stub
		List<MessageModel> msgs = messageRepo.findAll();
		List<MessageModel> studentMsgs = msgs.stream().filter(msg -> msg.getOwnerMail().equals(email) && msg.getAnswer().equals("")).collect(Collectors.toList());
		return studentMsgs;
	}

	public MessageModel getMsgById(Long id) {
		// TODO Auto-generated method stub
		return messageRepo.findMessageById(id);

	}

	public List<HouseDetailsModel> getAllHousesDetailsByEmail(String email) {
		// TODO Auto-generated method stub

		List<HouseModel> houses = houseRepo.findAllByEmailId(email);

		List<HouseDetailsModel> houseDetails = new ArrayList<HouseDetailsModel>();

		houses.forEach(house -> {
			houseDetails.add(houseDetailsRepo.findHouseDetails(house.getId()));
		});

		return houseDetails;
	}

	public HouseDetailsModel getHouseDetailsById(Long id) {
		// TODO Auto-generated method stub
		return houseDetailsRepo.findHouseDetails(id);
	}

	public HouseAttributesModel getHouseAttributes(Long id) {
		// TODO Auto-generated method stub
		return houseAttributesRepo.findHouseAttributes(id);
	}

	public AddressModel getHouseAddress(Long id) {
		// TODO Auto-generated method stub
		return addressRepo.findHouseAddress(id);
	}

	public void updateHouse(HouseModel house, String houseOwneremail, MultipartFile houseImage, MultipartFile doc,
			String houseName, String houseRent, String houseContact, String noOfBedrooms, String noOfBathrooms,
			String doorNo, String street, String city, String zipCode, String parking, String petFriendly, String lawn,
			String isHide, String isBooked,
			String availableFrom) {

		house.setHouseOwnerMail(houseOwneremail);

		HouseModel savedHouse = houseRepo.save(house);

		HouseDetailsModel houseDetails = houseDetailsRepo.findHouseDetails(savedHouse.getId());

		houseDetails.setHouse(savedHouse);
		houseDetails.setHouseName(houseName);
		houseDetails.setHouseRent(houseRent);
		houseDetails.setHouseContact(houseContact);
		houseDetails.setNoOfBedrooms(noOfBedrooms);
		houseDetails.setNoOfBathrooms(noOfBathrooms);
		houseDetails.setAvailableFrom(availableFrom);
		try {
			if(houseImage.getSize() != 0) {
			houseDetails.setHousePhoto(Base64.getEncoder().encodeToString(houseImage.getBytes()));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		HouseDetailsModel savedHouseDetails = houseDetailsRepo.save(houseDetails);


		HouseAttributesModel houseAttributes = houseAttributesRepo.findHouseAttributes(savedHouseDetails.getId());

		houseAttributes.setDetails(savedHouseDetails);
		if(!lawn.isEmpty())
		houseAttributes.setLawn(lawn);


		System.out.println("---"+lawn+"---");

		houseAttributes.setNoOfBedrooms(noOfBedrooms);

		if(!parking.isEmpty())
		houseAttributes.setParking(parking);

		if(!petFriendly.isEmpty())
		houseAttributes.setPetFriendly(petFriendly);

		houseAttributesRepo.save(houseAttributes);

		HouseDocumentModel houseDocument = houseDocumentRepo.findHouseDocument(savedHouse.getId());

		houseDocument.setHouse(savedHouse);
		try {
			if(doc.getSize() != 0) {
//			houseDocument.setDocument(Base64.getEncoder().encodeToString(doc.getBytes()));
				houseDocument.setDocument(doc.getBytes());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		houseDocumentRepo.save(houseDocument);


		HousePropertiesModel houseProperty = housePropertiesRepo.findHouseProperties(savedHouse.getId());

		houseProperty.setHouse(savedHouse);

		if(!isBooked.isEmpty())
		houseProperty.setIsBooked(isBooked);

		if(!isHide.isEmpty())
		houseProperty.setIsHide(isHide);
		HousePropertiesModel savedHouseProperty = housePropertiesRepo.save(houseProperty);


		HouseStatusModel houseStatus = houseStatusRepo.findHouseStatus(savedHouseProperty.getId());

		if(!isBooked.isEmpty())
		houseStatus.setIsBooked(isBooked);

		if(!isHide.isEmpty())
		houseStatus.setIsHide(isHide);
		houseStatus.setProperty(savedHouseProperty);

		houseStatusRepo.save(houseStatus);

		AddressModel address = addressRepo.findHouseAddress(savedHouse.getId());

		if(!city.isEmpty())
		address.setCity(city);
		address.setDoorNo(doorNo);
		address.setStreet(street);
		address.setZipCode(zipCode);


		addressRepo.save(address);


	}

	public List<ReviewOwnerModel> getAllMyReviews(String email) {
		// TODO Auto-generated method stub
		return reviewOwnerRepo.findMyAllReviews(email);
	}

	public List<ReviewPropertyModel> getAllPropertyReviews(String email) {
		// TODO Auto-generated method stub

		 List<ReviewPropertyModel> reviews = new  ArrayList<ReviewPropertyModel>();

		reviewPropertyRepo.findAll().forEach(property -> {

			houseRepo.findAll().forEach(house -> {
				if(property.getHouseId().equals(house.getId().toString()) && house.getHouseOwnerMail().equals(email)) {
					reviews.add(property);
				}
			});

		});


		return reviews;
	}






}
