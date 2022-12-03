package com.housebooking.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name = "house_like_and_dislikes")
public class HouseLikeOrDislikeModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private int likes;
	private int dislikes;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_property_id", nullable = false)
    private HousePropertiesModel property;
	
}
