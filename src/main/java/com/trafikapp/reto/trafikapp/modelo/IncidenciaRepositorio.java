package com.trafikapp.reto.trafikapp.modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Integer>{
    Incidencia findById(int id);
    List<Incidencia> findBySourceId(Integer source);
}
