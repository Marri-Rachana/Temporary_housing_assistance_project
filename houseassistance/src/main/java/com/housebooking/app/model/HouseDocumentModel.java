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
@Table(name = "house_documents")
public class HouseDocumentModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Lob
	private byte[] document;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
    private HouseModel house;
	

	
}
