package com.trafikapp.reto.trafikapp.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Incidencia {
    @Id 
    @Column(unique = true)
    private String incidenceId;

    @Column(name = "source_id")
    private int sourceId;
    
    private String incidenceType;
    private String autonomousRegion;
    private String province;
    private String carRegistration;
    private String cause;
    private String cityTown;
    private String startDate;
    private String road;
    private String pkStart;
    private String pkEnd;
    private String direction;
    private Double latitude;
    private Double longitude;
    private Boolean creada = false;

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Sources source;
   
    @PrePersist
    public void prePersist() {
        if (this.incidenceId == null) {
            String generatedId = System.currentTimeMillis() + "-creada";
            this.incidenceId = generatedId;
        }
    }
}
