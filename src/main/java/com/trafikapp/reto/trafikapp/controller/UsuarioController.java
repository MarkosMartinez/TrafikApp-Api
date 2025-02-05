package com.trafikapp.reto.trafikapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trafikapp.reto.trafikapp.modelo.Usuario;
import com.trafikapp.reto.trafikapp.modelo.UsuarioRepositorio;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

@RestController
@RequiredArgsConstructor
@Tag(name = "Controlador Usuario", description = "Permite ver y gestionar los usuarios de la aplicación y loguearse en ella")
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;

    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "404", description = "Error, puede que el email no sea valido"),
		@ApiResponse(responseCode = "500", description = "Error del servidor")
	})
	@GetMapping("/api/usuarios/byEmail/{email}")
	public ResponseEntity<?> obtenerPorEmail(@PathVariable String email) {

		Usuario result = usuarioRepositorio.findByEmail(email);
		if(result == null) {
			return ResponseEntity.notFound().build();
			
		} else {
			return ResponseEntity.ok(result);
		}
	}

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Error, puede que alguna credencial no sea valida"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioRepositorio.findByEmail(usuario.getEmail());
        if (usuarioExistente != null && usuarioExistente.getContrasena().equals(DigestUtils.sha256Hex(usuario.getContrasena()))) {
            return ResponseEntity.ok(usuarioExistente);
        } else {
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Error, puede que algun dato no sea valido"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping("/api/crearusuario")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            if (usuario.getEmail() == null || usuario.getNombre() == null || usuario.getApellido() == null || usuario.getContrasena() == null) {
                return ResponseEntity.badRequest().body("Email, nombre, apellido y contraseña son obligatorios");
            }
            if (usuario.getRol() == null) {
                usuario.setRol("usuario");
            }
            usuario.setContrasena(DigestUtils.sha256Hex(usuario.getContrasena()));
            Usuario nuevoUsuario = usuarioRepositorio.save(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear el usuario: " + e.getMessage());
        }
    }
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Error al recuperar los usuarios. Tal vez no haya ningun usuario"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/api/usuarios")
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
    @PatchMapping("/api/modificarusuario")
    public ResponseEntity<?> modificarUsuario(@RequestBody Usuario usuarioActualizado) {
        try {
            Usuario usuarioExistente = usuarioRepositorio.findById(usuarioActualizado.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            if (usuarioActualizado.getEmail() == null || usuarioActualizado.getNombre() == null || usuarioActualizado.getApellido() == null) {
                return ResponseEntity.badRequest().body("Email, nombre y apellido son obligatorios");
            }

            usuarioExistente.setEmail(usuarioActualizado.getEmail());
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
            usuarioExistente.setRol(usuarioActualizado.getRol() != null ? usuarioActualizado.getRol() : "usuario");
            usuarioExistente.setContrasena(usuarioActualizado.getContrasena() != null ? DigestUtils.sha256Hex(usuarioActualizado.getContrasena()) : usuarioExistente.getContrasena());

            Usuario usuarioModificado = usuarioRepositorio.save(usuarioExistente);
            return ResponseEntity.ok(usuarioModificado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al modificar el usuario. Comprueba que los datos estén correctos: " + e.getMessage());
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Error, puede que el id del usuario no sea valido"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping("/api/eliminarusuario")
    public ResponseEntity<?> borrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioRepositorio.findByEmail(usuario.getEmail());
            if (usuarioExistente != null) {
                usuarioRepositorio.delete(usuarioExistente);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(400).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar el usuario: " + e.getMessage());
        }
    }

}
