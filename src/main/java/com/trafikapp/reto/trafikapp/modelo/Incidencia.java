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
public class Incidencia {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    private int id;
	private int incidenceId;
	private int sourceId;
	private String incidenceType;
	private String autonomousRegion;
    private String province;
    private String carRegistration;
    private String cause;
    private String startDate;
    private String road;
    private String pkStart;
    private String pkEnd;
    private	String direction;
    private Double latitude;
    private Double longitude;

}
