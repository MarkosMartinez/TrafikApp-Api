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

import com.trafikapp.reto.trafikapp.modelo.Usuario;
import com.trafikapp.reto.trafikapp.modelo.UsuarioRepositorio;
import com.trafikapp.reto.trafikapp.service.UsuarioService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Controlador Usuario", description = "Permite ver y gestionar los usuarios de la aplicación y loguearse en ella")
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private UsuarioService usuarioServicio;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Error, puede que alguna credencial no sea valida"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String contrasena) {
        Usuario usuario = usuarioServicio.login(email, contrasena);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
        return ResponseEntity.ok(usuario);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Error, puede que algun dato no sea valido"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/crearusuario")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioServicio.crearUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el usuario. Comprueba que los datos esten correctos");
        }
    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Error al recuperar los usuarios. Tal vez no haya ningun usuario"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerTodos() {

        List<Usuario> result = usuarioRepositorio.findAll();
        if(result.isEmpty()) {
            return ResponseEntity.notFound().build();
            
        }else {
            return ResponseEntity.ok(result);
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Error, puede que algun dato no sea valido"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PatchMapping("/modificarusuario")
    public ResponseEntity<?> modificarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioModificado = usuarioServicio.modificarUsuario(usuario);
            return ResponseEntity.ok(usuarioModificado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al modificar el usuario. Comprueba que los datos esten correctos: " + e);
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Error, puede que el id del usuario no sea valido"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping("/eliminarusuario")
    public ResponseEntity<?> borrarUsuario(@RequestBody String email) {
        try {
            usuarioServicio.borrarUsuario(email);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al borrar el usuario: " + e);
        }
    }

}
