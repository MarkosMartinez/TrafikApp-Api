package com.trafikapp.reto.trafikapp.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Camara {
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	private int cameraId;
	@Column(name = "source_id")
	private int sourceId;
	private String cameraName;
	private String urlImage;
	private Double latitude;
	private Double longitude;
	private String road;
	private String kilometer;
	private String address;

	
	@ManyToOne
	@JoinColumn(name = "source_id",  referencedColumnName = "id", insertable = false, updatable = false)
	private Sources source;
	
	

}
