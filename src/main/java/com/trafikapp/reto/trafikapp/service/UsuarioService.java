package com.trafikapp.reto.trafikapp.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trafikapp.reto.trafikapp.modelo.Usuario;
import com.trafikapp.reto.trafikapp.modelo.UsuarioRepositorio;

import jakarta.persistence.EntityNotFoundException;

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

    public Usuario modificarUsuario(Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepositorio.findById(usuarioActualizado.getId()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usuarioActualizado.getEmail() == null || usuarioActualizado.getNombre() == null || usuarioActualizado.getApellido() == null) {
            throw new IllegalArgumentException("Email, nombre y apellido son obligatorios");
        }

        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellido(usuarioActualizado.getApellido());
        usuarioExistente.setRol(usuarioActualizado.getRol() != null ? usuarioActualizado.getRol() : "usuario");
        usuarioExistente.setContrasena(usuarioActualizado.getContrasena() != null ? DigestUtils.sha256Hex(usuarioActualizado.getContrasena()) : usuarioExistente.getContrasena());

        return usuarioRepositorio.save(usuarioExistente);
    }

    @Transactional
    public void borrarUsuario(String email) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        if (usuario != null) {
            usuarioRepositorio.delete(usuario);
        } else {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
    }
}
