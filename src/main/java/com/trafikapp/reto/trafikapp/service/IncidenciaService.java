package com.trafikapp.reto.trafikapp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trafikapp.reto.trafikapp.SSLUtils;
import com.trafikapp.reto.trafikapp.modelo.Incidencia;
import com.trafikapp.reto.trafikapp.modelo.IncidenciaDTO;
import com.trafikapp.reto.trafikapp.modelo.IncidenciaRepositorio;

@Service
public class IncidenciaService {

    @Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private IncidenciaRepositorio incidenciaRepository;

    public void cargarDatosDesdeApiExterna() {
        incidenciaRepository.deleteAll();
        LocalDate today = LocalDate.now();
        String baseUrl = String.format("https://api.euskadi.eus/traffic/v1.0/incidences/byDate/%d/%02d/%02d", today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        SSLUtils.disableSslVerification();

        IncidenciaDTO incidenciaDTO = restTemplate.getForObject(baseUrl, IncidenciaDTO.class);
        if (incidenciaDTO != null) {
            int paginas = incidenciaDTO.getTotalPages();
            System.out.println("Guardando Incidencias...");

            for (int pagina = 1; pagina <= paginas; pagina++) {
                String url = baseUrl + "?_page=" + pagina;
                incidenciaDTO = restTemplate.getForObject(url, IncidenciaDTO.class);
                if (incidenciaDTO != null) {
                    for (Incidencia incidencia : incidenciaDTO.getIncidences()) {
                        incidenciaRepository.save(incidencia);
                    }
                }
            }
            System.out.println("Incidencias guardadas");
        }
    }

    @EventListener(ContextRefreshedEvent.class)
    @Order(3)
    public void cargarDatosAlInicio() {
        cargarDatosDesdeApiExterna();
    }

    public Incidencia addIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }
    
}
