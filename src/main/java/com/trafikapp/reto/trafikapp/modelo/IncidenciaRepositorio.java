package com.trafikapp.reto.trafikapp.modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Integer>{
    Incidencia findById(int id);
    List<Incidencia> findBySourceId(Integer source);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM incidencia i WHERE i.creada <> true", nativeQuery = true)
    void vaciarIncidenciasNoCreadas();
}
