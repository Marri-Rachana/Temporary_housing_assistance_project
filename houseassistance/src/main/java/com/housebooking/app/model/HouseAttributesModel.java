package com.housebooking.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "house_attributes")
public class HouseAttributesModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String noOfBedrooms;
	private String parking;
	private String petFriendly;
	private String lawn;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_details_id", nullable = false)
    private HouseDetailsModel details;
}
