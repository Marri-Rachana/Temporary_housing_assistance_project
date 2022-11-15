package com.housebooking.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class ReserveModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String houseId;
	private String userId;
	private String moveInDate;
	private String coupon;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String document1;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String document2;
}
