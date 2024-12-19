package com.trafikapp.reto.trafikapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trafikapp.reto.trafikapp.modelo.Incidencia;
import com.trafikapp.reto.trafikapp.modelo.IncidenciaRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IncidenciaController {

    private final IncidenciaRepositorio incidenciaRepositorio;

    @GetMapping("/incidencias")
    public ResponseEntity<?> obtenerTodos() {

        List<Incidencia> result = incidenciaRepositorio.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else {
            return ResponseEntity.ok(result);
        }
    }

}
