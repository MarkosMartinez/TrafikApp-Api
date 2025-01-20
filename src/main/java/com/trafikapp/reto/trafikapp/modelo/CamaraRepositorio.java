package com.trafikapp.reto.trafikapp.modelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.locationtech.proj4j.ProjCoordinate;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;


public interface CamaraRepositorio extends JpaRepository<Camara, Integer> {
    

    public default List<Camara> findAllFiltered() {

        // Transformación de coordenadas
        CRSFactory crsFactory = new CRSFactory();
        CoordinateTransformFactory transformFactory = new CoordinateTransformFactory();
        var utmCRS = crsFactory.createFromName("EPSG:25830");
        var wgs84CRS = crsFactory.createFromName("EPSG:4326");
        CoordinateTransform transform = transformFactory.createTransform(utmCRS, wgs84CRS);
    
        // Filtrado de cámaras
        return this.findAll().stream()
            .filter(camara -> camara.getUrlImage() != null 
                              && (camara.getUrlImage().startsWith("http://www.bizkaimove.com") 
                              || camara.getUrlImage().startsWith("https://www.vitoria-gasteiz.org")))
            // Transformación de coordenadas de UTM a Cordenadas Geográficas
            .map(camara -> {
                Double latitude = camara.getLatitude();
                Double longitude = camara.getLongitude();
    
                if (latitude != null && longitude != null) {
                    if (latitude > 90) {
                        ProjCoordinate utmCoord = new ProjCoordinate(longitude, latitude);
                        ProjCoordinate geoCoord = new ProjCoordinate();
    
                        transform.transform(utmCoord, geoCoord);
    
                        camara.setLatitude(geoCoord.y);
                        camara.setLongitude(geoCoord.x);
                    }
                }
                return camara;
            })
            .toList();
    }

    List<Camara> findBySourceId(Integer sourceId);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE camara", nativeQuery = true)
    void vaciarTabla();

}