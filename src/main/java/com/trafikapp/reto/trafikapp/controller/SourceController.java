package com.trafikapp.reto.trafikapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trafikapp.reto.trafikapp.modelo.Sources;
import com.trafikapp.reto.trafikapp.modelo.SourceRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SourceController {

	private final SourceRepositorio sourceRepositorio;

	@GetMapping("/sources")
	public ResponseEntity<?> obtenerTodos() {
		List<Sources> result=sourceRepositorio.findAll();
		if(result.isEmpty()) {
			return ResponseEntity.notFound().build();
			
		}else {
			//? sera la información. 
			return ResponseEntity.ok(result);
		}
	}

	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	/*@GetMapping("/producto/{id}")
	public ResponseEntity obtenerUno(@PathVariable Long id) {
		// Vamos a modificar este código
		Camaras producto= camaraRepositorio.findById(id).orElse(null); //Si no lo encuentra que devuelva null.
		if(producto ==null) { //Si la lista tiene elementos enviamos la lista.
			return ResponseEntity.notFound().build();
			
		}else {//Aqui devolveremos un ResponseEntity 400. PERO Lo que no podemos hacer es mandar 
			//una lista si va bien y sino uno 400. Haremos que el metodo devuelva un ReponseEntity<?> 
			//? sera la información. 
			return ResponseEntity.ok(producto);
		}
	
	}*/

}
