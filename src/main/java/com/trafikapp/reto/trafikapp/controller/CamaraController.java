package com.trafikapp.reto.trafikapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trafikapp.reto.trafikapp.modelo.Camara;
import com.trafikapp.reto.trafikapp.modelo.CamaraRepositorio;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Controlador Camara", description = "Permite ver información sobre las cámaras disponibles")
public class CamaraController {
	
	private final CamaraRepositorio camaraRepositorio;

	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
	@GetMapping("/camaras")
	public ResponseEntity<?> obtenerTodos() {

		List<Camara> result = camaraRepositorio.findAllFiltered();
		if(result.isEmpty()) {
			return ResponseEntity.notFound().build();
			
		}else {
			return ResponseEntity.ok(result);
		}
	}
}