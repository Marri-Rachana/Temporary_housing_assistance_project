package com.housebooking.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "house_properties")
public class HousePropertiesModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String isBooked;
	private String isHide;
	private String isVerified;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
    private HouseModel house;
	
	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "property")
    private HouseStatusModel houseStatus;
	
	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "property")
    private HouseLikeOrDislikeModel houseLikeOrDislike;

	
}
