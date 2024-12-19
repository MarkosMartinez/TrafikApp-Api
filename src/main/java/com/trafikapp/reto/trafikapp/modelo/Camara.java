package com.trafikapp.reto.trafikapp.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Camara {
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	private int cameraId;
	private int sourceId;
	private String cameraName;
	private String urlImage;
	private Double latitude;
	private Double longitude;
	private String road;
	private String kilometer;
	private String address;
	

}
