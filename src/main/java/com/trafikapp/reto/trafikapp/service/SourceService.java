package com.trafikapp.reto.trafikapp.service;

import com.trafikapp.reto.trafikapp.SSLUtils;
import com.trafikapp.reto.trafikapp.modelo.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SourceService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SourceRepositorio sourceRepositorio;

    public void cargarDatosDesdeApiExterna() {
        String url = "https://api.euskadi.eus/traffic/v1.0/sources";

        SSLUtils.disableSslVerification();
        SourceDTO[] sources = restTemplate.getForObject(url, SourceDTO[].class);

        for (int i = 0; i < sources.length; i++) {
            if (sources != null) {
                Sources source = new Sources();
                source.setId(Integer.parseInt(sources[i].getId()));
                source.setDescripcionEs(sources[i].getDescripcionEs());
                source.setDescripcionEu(sources[i].getDescripcionEu());

                sourceRepositorio.save(source);
            }
        }
        System.out.println("Sources actualizadas");
    }

    @EventListener(ContextRefreshedEvent.class)
    @Order(1)
    public void cargarDatosAlInicio() {
        cargarDatosDesdeApiExterna();
    }
}