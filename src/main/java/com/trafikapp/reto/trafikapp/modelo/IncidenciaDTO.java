package com.trafikapp.reto.trafikapp.modelo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenciaDTO {

    private int totalItems;
	private int totalPages;
	private int currentPage;
    private List <Incidencia> incidences;
}
