package com.housebooking.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class BookModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String houseId;
	private String userId;
	private String moveInDate;
	private String coupon;
	private String houseRent;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String document1;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String document2;
}
