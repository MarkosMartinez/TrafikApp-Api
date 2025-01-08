package com.trafikapp.reto.trafikapp.modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CamaraRepositorio extends JpaRepository<Camara, Integer> {

    public default List<Camara> findAllFiltered() {
        return this.findAll().stream()
            .filter(camara -> camara.getLatitude() != null && camara.getLongitude() != null && camara.getUrlImage() != null && (camara.getUrlImage().startsWith("http://www.bizkaimove.com") || camara.getUrlImage().startsWith("https://www.vitoria-gasteiz.org"))).toList(); //camara.getAddress() != null && camara.getKilometer() != null
    }
    List<Camara> findBySourceId(Integer sourceId);

}