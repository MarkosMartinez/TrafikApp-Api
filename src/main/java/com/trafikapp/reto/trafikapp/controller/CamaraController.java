package com.trafikapp.reto.trafikapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trafikapp.reto.trafikapp.modelo.Camara;
import com.trafikapp.reto.trafikapp.modelo.CamaraRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CamaraController {
	
	private final CamaraRepositorio camaraRepositorio;

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