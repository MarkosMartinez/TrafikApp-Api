package com.trafikapp.reto.trafikapp.modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CamaraRepositorio extends JpaRepository<Camara, Integer> {

    public default List<Camara> findAllFiltered() {
        return this.findAll().stream()
            .filter(camara -> camara.getKilometer() != null && camara.getLatitude() != null && camara.getLongitude() != null && camara.getUrlImage() != null && camara.getUrlImage().startsWith("http")).toList(); //camara.getAddress() != null
    }

}