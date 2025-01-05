package com.trafikapp.reto.trafikapp.modelo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
    Usuario findByEmail(String email);

}
