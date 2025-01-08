package com.trafikapp.reto.trafikapp.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity (name = "sources")
public class Sources {
    @Id
    
    private int id;
    private String descripcionEs;
    private String descripcionEu;

    @JsonIgnore
    @OneToMany(mappedBy = "source")
    private List<Camara> camaras = new ArrayList<Camara>();

    @JsonIgnore
    @OneToMany(mappedBy = "source")
    private List<Incidencia> incidencias = new ArrayList<Incidencia>();

}