package com.trafikapp.reto.trafikapp.modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CamaraRepositorio extends JpaRepository<Camara, Integer> {

    public default List<Camara> findAllFiltered() {
        return this.findAll().stream()
            .filter(camara -> camara.getLatitude() != null && camara.getLongitude() != null && camara.getUrlImage() != null && (camara.getUrlImage().startsWith("http://www.bizkaimove.com") || camara.getUrlImage().startsWith("https://www.vitoria-gasteiz.org"))).toList(); //camara.getAddress() != null && camara.getKilometer() != null
    }
    List<Camara> findBySourceId(Integer sourceId);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE camara", nativeQuery = true)
    void vaciarTabla();

}