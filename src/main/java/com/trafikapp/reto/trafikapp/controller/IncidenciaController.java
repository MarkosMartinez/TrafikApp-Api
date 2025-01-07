package com.trafikapp.reto.trafikapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/crearincidencia")
    public ResponseEntity<?> crearIncidencia(@RequestBody Incidencia nuevaIncidencia) {
        try {
            nuevaIncidencia.setCreada(true);
            incidenciaRepositorio.save(nuevaIncidencia);
            return ResponseEntity.ok(nuevaIncidencia);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear la incidencia: " + e.getMessage());
        }
    }

    @PatchMapping("/modificarincidencia")
    public ResponseEntity<?> modificarIncidencia(@RequestBody Incidencia incidenciaModificada) {
        try {
            Incidencia incidencia = incidenciaRepositorio.findById(incidenciaModificada.getId());
            if (incidencia == null) {
                return ResponseEntity.badRequest().body("No se ha encontrado ninguna incidencia con el id: " + incidenciaModificada.getId());
            }
            incidencia.setIncidenceType(incidenciaModificada.getIncidenceType());
            incidencia.setCause(incidenciaModificada.getCause());
            incidencia.setStartDate(incidenciaModificada.getStartDate());
            incidencia.setLatitude(incidenciaModificada.getLatitude());
            incidencia.setLongitude(incidenciaModificada.getLongitude());
            return ResponseEntity.ok(incidenciaRepositorio.save(incidencia));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al modificar la incidencia: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminarincidencia")
    public ResponseEntity<?> eliminarIncidencia(@RequestBody Incidencia incidenciaAEliminar) {
        Incidencia incidencia = incidenciaRepositorio.findById(incidenciaAEliminar.getId());
        if (incidencia == null) {
            return ResponseEntity.badRequest().body("No se ha encontrado ninguna incidencia con el id: " + incidenciaAEliminar.getId());
        }
        incidenciaRepositorio.delete(incidencia);
        return ResponseEntity.ok("Incidencia eliminada");
    }

}
