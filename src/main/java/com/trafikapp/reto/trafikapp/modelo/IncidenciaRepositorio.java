package com.trafikapp.reto.trafikapp.modelo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Integer>{
    Incidencia findById(int id);
}
