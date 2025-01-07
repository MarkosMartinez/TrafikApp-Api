package com.trafikapp.reto.trafikapp.service;

import com.trafikapp.reto.trafikapp.SSLUtils;
import com.trafikapp.reto.trafikapp.modelo.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SourceService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SourceRepositorio sourceRepositorio;

    public void cargarDatosDesdeApiExterna() {
        // URL de la API externa
        String url = "https://api.euskadi.eus/traffic/v1.0/sources";

        SSLUtils.disableSslVerification();
        // Llama a la API y obtiene los datos como un arreglo de ProductoDTO
        SourceDTO[] sources = restTemplate.getForObject(url, SourceDTO[].class);

        for (int i = 0; i < sources.length; i++) {
            if (sources != null) {
                // Convertir y guardar cada ProductoDTO en la base de datos
                Sources source = new Sources();
                // inci.setIncidenceId(Long.parseLong(incidencia.getIncidenceId()));
                source.setId(Long.parseLong(sources[i].getId()));
                source.setDescripcionEs(sources[i].getDescripcionEs());
                source.setDescripcionEu(sources[i].getDescripcionEu());

                sourceRepositorio.save(source);
            }
        }
    }

    // Si queremos que los datos se carguen automáticamente cada vez que la
    // aplicación arranca,
    // podemos usar el @EventListener en el método cargarDatosDesdeApi.
    @EventListener(ContextRefreshedEvent.class)
    public void cargarDatosAlInicio() {
        cargarDatosDesdeApiExterna();
    }
}