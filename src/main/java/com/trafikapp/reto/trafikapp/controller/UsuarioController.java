package com.trafikapp.reto.trafikapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trafikapp.reto.trafikapp.UsuarioService;
import com.trafikapp.reto.trafikapp.modelo.Usuario;
import com.trafikapp.reto.trafikapp.modelo.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private UsuarioService usuarioServicio;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String contrasena) {
        Usuario usuario = usuarioServicio.login(email, contrasena);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/crearusuario")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioServicio.crearUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el usuario. Comprueba que los datos esten correctos");
        }
    }
    
    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerTodos() {

        List<Usuario> result = usuarioRepositorio.findAll();
        if(result.isEmpty()) {
            return ResponseEntity.notFound().build();
            
        }else {
            return ResponseEntity.ok(result);
        }
    }

    @PatchMapping("/modificarusuario")
    public ResponseEntity<?> modificarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioModificado = usuarioServicio.modificarUsuario(usuario);
            return ResponseEntity.ok(usuarioModificado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al modificar el usuario. Comprueba que los datos esten correctos: " + e);
        }
    }

    @DeleteMapping("/eliminarusuario")
    public ResponseEntity<?> borrarUsuario(@RequestParam String email) {
        try {
            usuarioServicio.borrarUsuario(email);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al borrar el usuario: " + e);
        }
    }

}
