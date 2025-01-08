package com.trafikapp.reto.trafikapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trafikapp.reto.trafikapp.SSLUtils;
import com.trafikapp.reto.trafikapp.modelo.Camara;
import com.trafikapp.reto.trafikapp.modelo.CamaraDTO;
import com.trafikapp.reto.trafikapp.modelo.CamaraRepositorio;

@Service
public class CamaraService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CamaraRepositorio camaraRepository;
	
	

public void cargarDatosDesdeApiExterna() {
	camaraRepository.deleteAll();
    String baseUrl = "https://api.euskadi.eus/traffic/v1.0/cameras";
    SSLUtils.disableSslVerification();

    CamaraDTO camarasDTO = restTemplate.getForObject(baseUrl, CamaraDTO.class);
    if (camarasDTO != null) {
        int paginas = camarasDTO.getTotalPages();
        System.out.println("Guardando Camaras...");

        for (int pagina = 1; pagina <= paginas; pagina++) {
            String url = baseUrl + "?_page=" + pagina;
            camarasDTO = restTemplate.getForObject(url, CamaraDTO.class);
            if (camarasDTO != null) {
                for (Camara camara : camarasDTO.getCameras()) {
                    camaraRepository.save(camara);
                }
            }
        }
        System.out.println("Camaras guardadas");
    }
}

	
	@EventListener(ContextRefreshedEvent.class)
    @Order(2)
    public void cargarDatosAlInicio() {
        cargarDatosDesdeApiExterna();
    }
	
}