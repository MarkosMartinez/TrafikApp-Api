package com.trafikapp.reto.trafikapp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity (name = "sources")
public class Sources {
    @Id
    private Long id;
    private String descripcionEs;
    private String descripcionEu;

}