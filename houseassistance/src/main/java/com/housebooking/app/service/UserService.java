package com.housebooking.app.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.Comparator.comparingLong;

import com.housebooking.app.dao.AddressRepo;
import com.housebooking.app.dao.AppointmentRepo;
import com.housebooking.app.dao.CouponRepo;
import com.housebooking.app.dao.DislikesModelRepo;
import com.housebooking.app.dao.FavouritesRepo;
import com.housebooking.app.dao.HouseAttributesRepo;
import com.housebooking.app.dao.HouseDetailsRepo;
import com.housebooking.app.dao.HouseLikeOrDislikeRepo;
import com.housebooking.app.dao.HousePropertiesRepo;
import com.housebooking.app.dao.HouseRepo;
import com.housebooking.app.dao.HouseStatusRepo;
import com.housebooking.app.dao.LikesModelRepo;
import com.housebooking.app.dao.MessageRepo;
import com.housebooking.app.dao.BookRepo;
import com.housebooking.app.dao.ReviewOwnerRepo;
import com.housebooking.app.dao.ReviewPropertyRepo;
import com.housebooking.app.model.AppointmentModel;
import com.housebooking.app.model.Coupon;
import com.housebooking.app.model.DislikesModel;
import com.housebooking.app.model.FavouritesModel;
import com.housebooking.app.model.HouseDetailsModel;
import com.housebooking.app.model.HouseLikeOrDislikeModel;
import com.housebooking.app.model.HouseModel;
import com.housebooking.app.model.HousePropertiesModel;
import com.housebooking.app.model.HouseStatusModel;
import com.housebooking.app.model.LikesModel;
import com.housebooking.app.model.MessageModel;
import com.housebooking.app.model.ReserveModel;
import com.housebooking.app.model.BookModel;
import com.housebooking.app.model.ReviewOwnerModel;
import com.housebooking.app.model.ReviewPropertyModel;

@Service
public class UserService {

	@Autowired
	private HouseRepo houseRepo;

	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private FavouritesRepo favouritesRepo;

	@Autowired
	private AppointmentRepo appointmentRepo;

	@Autowired
	private ReviewOwnerRepo reviewOwnerRepo;

	@Autowired
	private HouseDetailsRepo houseDetailsRepo;

	@Autowired
	private HouseAttributesRepo houseAttributesRepo;

	@Autowired
	private HouseLikeOrDislikeRepo houseLikeOrDislikeRepo;

	@Autowired
	private HousePropertiesRepo housePropertiesRepo;

	@Autowired
	private HouseStatusRepo houseStatusRepo;

	@Autowired
	private LikesModelRepo likesModelRepo;

	@Autowired
	private DislikesModelRepo dislikesModelRepo;

	@Autowired
	private ReviewPropertyRepo reviewPropertyRepo;

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private CouponRepo couponRepo;

	public List<HouseDetailsModel> getAllHouseDetails(){
		List<HouseDetailsModel> houseDetails;
        try {
			 houseDetails = new ArrayList<HouseDetailsModel>();

			List<HouseStatusModel> houseStatus = houseStatusRepo.findAll();

			houseStatus.forEach(status -> {
				if (status.getIsBooked().equals("0") && status.getIsHide().equals("0") && status.getIsVerified().equals("1")) {
					houseDetails.add(houseDetailsRepo.findHouseDetails(status.getId()));
				}
			});
		}
		catch (Exception e)
		{
			return null;
		}

		return houseDetails;
	}

	public BookModel saveBookHouse(BookModel book) {
		// TODO Auto-generated method stub

		if(book.getCoupon().equals("")) {

			HousePropertiesModel houseProperty = housePropertiesRepo.findHouseProperties(Long.parseLong(book.getHouseId()));

			houseProperty.setIsBooked("1");
			housePropertiesRepo.save(houseProperty);

			HouseStatusModel houseStatus = houseStatusRepo.findHouseStatus(Long.parseLong(book.getHouseId()));

			houseStatus.setIsBooked("1");
			houseStatusRepo.save(houseStatus);

			return bookRepo.save(book);
		}

		List<Coupon> coupons = couponRepo.findAll();

		List<Coupon> coupon = coupons.stream().filter(co -> co.getCouponCode().equals(book.getCoupon())).collect(Collectors.toList());

		if(coupon.size() != 0) {

			Integer houseRent = Integer.parseInt(book.getHouseRent()) - Integer.parseInt(coupon.get(0).getDiscountAmount());

			book.setHouseRent(houseRent.toString());



			couponRepo.deleteById(coupon.get(0).getId());
			HousePropertiesModel houseProperty = housePropertiesRepo.findHouseProperties(Long.parseLong(book.getHouseId()));

			houseProperty.setIsBooked("1");
			housePropertiesRepo.save(houseProperty);

			HouseStatusModel houseStatus = houseStatusRepo.findHouseStatus(Long.parseLong(book.getHouseId()));

			houseStatus.setIsBooked("1");
			houseStatusRepo.save(houseStatus);

			return bookRepo.save(book);
		}
		else {

			return null;
		}


	}

	public int savefavourites(FavouritesModel favourite) {
		// TODO Auto-generated method stub
		try {
			favouritesRepo.save(favourite);
		}
		catch (Exception e)
		{
			return 0;
		}
		return 1;
	}

	public void likeHouse(Long id, Long userId) {
		// TODO Auto-generated method stub
		HouseModel house = houseRepo.findHouseById(id);

		HouseLikeOrDislikeModel likeordislike = houseLikeOrDislikeRepo.findLikeOrDislikeById(house.getId());

		likeordislike.setLikes(likeordislike.getLikes() + 1);
		houseLikeOrDislikeRepo.save(likeordislike);

		LikesModel likes = new LikesModel();

		likes.setHouse_id(id.toString());
		likes.setUser_id(userId.toString());

		likesModelRepo.save(likes);

		List<DislikesModel> dislike = new ArrayList<DislikesModel>();

		dislikesModelRepo.findAll().forEach(dislikes -> {
			if(dislikes.getHouse_id().equals(id.toString()) && dislikes.getUser_id().equals(userId.toString())) {
				dislike.add(dislikes);

			}
		});

		if(dislike.size() > 0) {
		dislikesModelRepo.delete(dislike.get(0));
		}

	}

	public void disLikeHouse(Long id, Long userId) {
		// TODO Auto-generated method stub
		HouseModel house = houseRepo.findHouseById(id);

		HouseLikeOrDislikeModel likeordislike = houseLikeOrDislikeRepo.findLikeOrDislikeById(house.getId());

		likeordislike.setDislikes(likeordislike.getDislikes() + 1);

		houseLikeOrDislikeRepo.save(likeordislike);

		DislikesModel dislikes = new DislikesModel();

		dislikes.setHouse_id(id.toString());
		dislikes.setUser_id(userId.toString());

		dislikesModelRepo.save(dislikes);

		List<LikesModel> like = new ArrayList<LikesModel>();

		likesModelRepo.findAll().forEach(likes -> {
			if(likes.getHouse_id().equals(id.toString()) && likes.getUser_id().equals(userId.toString())) {
				like.add(likes);

			}
		});

		if(like.size() > 0) {
			likesModelRepo.delete(like.get(0));
		}


	}

	public int saveAppointment(AppointmentModel appointment) {
        try {
			appointmentRepo.save(appointment);
		}
		catch (Exception e)
		{
			return 0;
		}
        return 1;
	}

	public List<AppointmentModel> getAllAppointmentsByUserId(String email) {
		List<AppointmentModel> appointments;
		try {
			appointments = new ArrayList<AppointmentModel>();

			appointmentRepo.findAll().forEach(apt -> {

				houseRepo.findAll().forEach(house -> {
					if (apt.getHouseId().equals(house.getId().toString()) && house.getHouseOwnerMail().equals(email)) {
						appointments.add(apt);
					}
				});

			});
		}
		catch (Exception e)
		{
			return null;
		}

		return appointments;
	}

	public int saveReviewOwner(ReviewOwnerModel review) {
		try {
			reviewOwnerRepo.save(review);
		}
		catch (Exception e)
		{
			return 0;
		}
		return 1;
	}

	public int saveMsg(MessageModel msg) {
		try {
			messageRepo.save(msg);
		}
		catch (Exception e)
		{
			return 0;
		}
        return 1;
	}

	public List<MessageModel> findAllMessages(String email) {
		// TODO Auto-generated method stub
		List<MessageModel> ownerMsgs;
		try {
			List<MessageModel> msgs = messageRepo.findAll();
			ownerMsgs = msgs.stream().filter(msg -> msg.getStudentMail().equals(email) && !msg.getAnswer().equals("")).collect(Collectors.toList());
		}
		catch (Exception e)
		{
			return null;
		}
		return ownerMsgs;
	}

	public List<HouseDetailsModel> searchHouses(String searchKey) {
		// TODO Auto-generated method stub
		List<HouseDetailsModel> searchedHouses;
		try {
			List<HouseDetailsModel> houses = houseDetailsRepo.findAll();
			 searchedHouses = houses.stream().filter(house -> house.getHouseName().contains(searchKey) ||
					house.getHouseRent().equals(searchKey) || house.getAvailableFrom().contains(searchKey)
					|| house.getNoOfBedrooms().equals(searchKey) || house.getNoOfBathrooms().equals(searchKey)).collect(Collectors.toList());
		}
		catch (Exception e)
		{
			return null;
		}
        return searchedHouses;
	}

	public List<HouseDetailsModel> filterHouses(String city, String moveInDate) {
		// TODO Auto-generated method stub
		List<HouseDetailsModel> filteredHouses;
		try {
			List<HouseDetailsModel> houses = houseDetailsRepo.findAll();

			filteredHouses = houses.stream().filter(house -> house.getAvailableFrom().equals(moveInDate)).collect(Collectors.toList());

			addressRepo.findAll().forEach(address -> {
				if (address.getCity().equals(city)) {
					houseDetailsRepo.findAll().forEach(details -> {
						if (details.getId().equals(address.getHouseId())) {
							filteredHouses.add(details);
						}
					});
				}
			});
		}
		catch (Exception e)
		{
			return null;
		}

		return filteredHouses.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(HouseDetailsModel::getId))),
                ArrayList::new));

	}

	public List<HouseDetailsModel> advanceFilterHouses(String city, String moveInDate, String parking, String petFriendly,
			String lawn, String houseType) {


		List<HouseDetailsModel> advnaceFilteredHouses  = new ArrayList<HouseDetailsModel>();
        try {
			houseRepo.findAll().forEach(house -> {
				if (house.getHouseType().equals(houseType)) {
					advnaceFilteredHouses.add(houseDetailsRepo.findHouseDetails(house.getId()));
				}
			});

			addressRepo.findAll().forEach(address -> {
				if (address.getCity().equals(city)) {
					houseDetailsRepo.findAll().forEach(details -> {
						if (details.getId().equals(address.getHouseId())) {
							advnaceFilteredHouses.add(details);
						}
					});
				}
			});

			houseDetailsRepo.findAll().forEach(details -> {
				if (details.getAvailableFrom().equals(moveInDate)) {
					advnaceFilteredHouses.add(details);
				}
			});

			houseAttributesRepo.findAll().forEach(attr -> {

				if (attr.getLawn().equals(lawn) || attr.getParking().equals(parking) || attr.getPetFriendly().equals(petFriendly)) {
					advnaceFilteredHouses.add(attr.getDetails());
				}
			});
		}
		catch (Exception e)
		{
			return null;
		}

		return advnaceFilteredHouses.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(HouseDetailsModel::getId))),
                ArrayList::new));
	}

	public List<HouseDetailsModel> sort(String price) {
		// TODO Auto-generated method stub

			List<HouseDetailsModel> houses = houseDetailsRepo.findAll();
			String startprice, endprice;
			int sp, ep;
			startprice = price.split("to")[0];
			System.out.println("-------------" + startprice);
			endprice = price.split("to")[1];
			sp = Integer.parseInt(startprice);
			ep = Integer.parseInt(endprice);
		List<HouseDetailsModel> advnaceFilteredHouses = houses.stream().filter(
					house -> Integer.parseInt(house.getHouseRent()) >= sp && Integer.parseInt(house.getHouseRent()) <= ep).collect(Collectors.toList());

			Comparator<HouseDetailsModel> rentComparator =
					(house1, house2) -> house1.getHouseRent().compareTo(house2.getHouseRent());
		return advnaceFilteredHouses.stream().sorted(rentComparator).collect(Collectors.toList());

	}

	public int reserveHouse(Long houseId, Long userId) {
		// TODO Auto-generated method stub
		try {
			ReserveModel reserve = new ReserveModel();

			reserve.setHouseId(houseId);
			reserve.setUserId(userId);

			HousePropertiesModel houseProperty = housePropertiesRepo.findHouseProperties(houseId);

			houseProperty.setIsHide("1");
			housePropertiesRepo.save(houseProperty);

			HouseStatusModel houseStatus = houseStatusRepo.findHouseStatus(houseId);

			houseStatus.setIsHide("1");
			houseStatusRepo.save(houseStatus);
		}
		catch (Exception e){
			return 0;
		}

		return 1;
	}

	public int saveReviewProperty(ReviewPropertyModel property) {
		// TODO Auto-generated method stub
        try {
			reviewPropertyRepo.save(property);
		}
		catch (Exception e)
		{
			return 0;
		}
		return 1;
	}

	public List<HouseDetailsModel> findAllFavs(Long id) {
		// TODO Auto-generated method stub
		List<HouseDetailsModel> houseDetails;
        try {
			List<FavouritesModel> favs = favouritesRepo.findUserFavs(id);
			houseDetails = new ArrayList<HouseDetailsModel>();

			favs.forEach(fav -> {
				houseDetails.add(houseDetailsRepo.findHouseDetails(Long.parseLong(fav.getHouseId())));
			});
		}
		catch (Exception e)
		{
			return null;
		}

		return houseDetails;
	}

	public List<BookModel> getAllBookingsByUserId(Long id) {
		// TODO Auto-generated method stub
		List<BookModel> bookModels;
		try {
			bookModels = bookRepo.getAllBookingsOfUser(id);
		}
		catch (Exception e)
		{
			return null;
		}
		return bookModels;
	}

	public int getIsLikedByUser(Long houseId, Long userId) {
		// TODO Auto-generated method stub

		LikesModel likes = likesModelRepo.findIsLikedByUser(houseId, userId);

		if(likes == null)
		{
			return 0;
		}
		else {
			return 1;
		}

	}

	public int getIsDisLikedByUser(Long houseId, Long userId) {
		// TODO Auto-generated method stub
		DislikesModel dislikes = dislikesModelRepo.findIsDisLikedByUser(houseId, userId);

		if(dislikes == null)
		{
			return 0;
		}
		else {
			return 1;
		}
	}


}
