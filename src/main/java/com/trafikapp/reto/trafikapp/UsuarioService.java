package com.trafikapp.reto.trafikapp;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trafikapp.reto.trafikapp.modelo.Usuario;
import com.trafikapp.reto.trafikapp.modelo.UsuarioRepositorio;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Usuario login(String email, String password) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        if (usuario != null && usuario.getContrasena().equals(DigestUtils.sha256Hex(password))) {
            return usuario;
        }
        return null;
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getNombre() == null || usuario.getApellido() == null || usuario.getContrasena() == null) {
            throw new IllegalArgumentException("Email, nombre, apellido y contraseña son obligatorios");
        }
        if (usuario.getRol() == null) {
            usuario.setRol("usuario");
        }
        usuario.setContrasena(DigestUtils.sha256Hex(usuario.getContrasena()));
        return usuarioRepositorio.save(usuario);
    }

}
