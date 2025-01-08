package com.trafikapp.reto.trafikapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trafikapp.reto.trafikapp.modelo.Sources;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.trafikapp.reto.trafikapp.modelo.SourceRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Controlador Source", description = "Permite ver los sources disponibles")
public class SourceController {

	private final SourceRepositorio sourceRepositorio;

	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
	@GetMapping("/api/sources")
	public ResponseEntity<?> obtenerTodos() {

		List<Sources> result=sourceRepositorio.findAll();
		if(result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(result);
		}
	}

}
