package com.housebooking.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "house_details")
public class HouseDetailsModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String houseName;
	private String houseRent;
	private String noOfBedrooms;
	private String noOfBathrooms;
	private String houseContact;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String housePhoto;
	private String availableFrom;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
    private HouseModel house;
	
	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "details")
    private HouseAttributesModel houseAttributes;


	
}
