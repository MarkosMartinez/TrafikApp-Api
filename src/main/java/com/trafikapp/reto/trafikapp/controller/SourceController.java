package com.trafikapp.reto.trafikapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trafikapp.reto.trafikapp.modelo.Sources;
import com.trafikapp.reto.trafikapp.modelo.SourceRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SourceController {

	private final SourceRepositorio sourceRepositorio;

	/**
	 * Obtenemos todos los productos
	 * Devolvemos 200 OK si encontramos el recurso
	 * Si no, devolvemos 400 Not found
	 * @return
	 */
	/* Esto es un EndPoint:
	 * En el contexto de desarrollo web y APIs, un endpoint es un punto de acceso específico 
	 * a un servicio, generalmente asociado a una URL, que permite la comunicación entre un 
	 * cliente y un servidor. Los endpoints representan rutas específicas en una API 
	 * que manejan solicitudes y devuelven respuestas.
	 * Cada endpoint está asociado a una URL única, que representa una ubicación en la API.
	 * Por ejemplo, en una API de productos, 
	 * un endpoint para obtener la lista de productos podría estar en /api/productos.
	 * En nuestro caso api sera: localhost:8080 ya que tenemos el servidor en nuestro ordenador(local) 
	 * 
	 * GET: Para obtener datos o recursos.
	   POST: Para crear nuevos recursos.
	   PUT o PATCH: Para actualizar recursos existentes.
	   DELETE: Para eliminar recursos.
	 * 
	 * */
	@GetMapping("/sources")
	public ResponseEntity<?> obtenerTodos() {
		
		//Aqui tenemos que devolver todos los productos.

		List<Sources> result=sourceRepositorio.findAll();
		if(result.isEmpty()) { //Si la lista tiene elementos enviamos la lista.
			return ResponseEntity.notFound().build();
			
		}else {//Aqui devolveremos un ResponseEntity 400. PERO Lo que no podemos hacer es mandar 
			//una lista si va bien y sino uno 400. Haremos que el metodo devuelva un ReponseEntity<?> 
			//? sera la información. 
			return ResponseEntity.ok(result);
		}
	}

	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	/*@GetMapping("/producto/{id}")
	public ResponseEntity obtenerUno(@PathVariable Long id) {
		// Vamos a modificar este código
		Camaras producto= camaraRepositorio.findById(id).orElse(null); //Si no lo encuentra que devuelva null.
		if(producto ==null) { //Si la lista tiene elementos enviamos la lista.
			return ResponseEntity.notFound().build();
			
		}else {//Aqui devolveremos un ResponseEntity 400. PERO Lo que no podemos hacer es mandar 
			//una lista si va bien y sino uno 400. Haremos que el metodo devuelva un ReponseEntity<?> 
			//? sera la información. 
			return ResponseEntity.ok(producto);
		}
	
	}*/

	/**
	 * Insertamos un nuevo producto
	 * 
	 * @param nuevo
	 * @return producto insertado
	 * 201 si ha sido creado
	 */
	
	//Cualquier solicitud POST a /producto se dirigirá a este método.
	//RequestBody nos inyecta el cuerpo de una peticion dentro de un objeto.
	//En este caso nos mandaran un json con la informacion del objecto en el navegador y 
	//lo que hace es convertirlo en un objeto Producto para que desde Java lo podramos guardar en la BD.
	//La solicitud HTTP que se envía a este método podría tener un cuerpo JSON como este:
	/*{
	    "nombre": "Jamon de bellota",
	    "precio": 120.0
	} Lo mandamos usando Postman, opcion POST url:localhost:8080/producto y en el body del html en raw  */
	//
	/*@PostMapping("/producto")
	public ResponseEntity<Camaras> nuevoProducto(@RequestBody Camaras nuevo) {
		
		//Cuando se haga una peticion post para crear un recurso,
		//ademas de devolver un 201 se devuelve la URI donde podemos consultar ese producto.
		Camaras saved= camaraRepositorio.save(nuevo);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		//En el body del html nos va a devolver el producto en un json con el id que le ha asignado.
		
		/*{
    	"id": 19,
    	"nombre": "Jamon de bellota",
    	"precio": 120.0
		} */
	/*}*/

	/**
	 * 
	 * @param editar
	 * @param id
	 * Devolvemos 200 ok si encontramos y modificamos el producto
	 * Si no 404 not found 
	 * @return
	 */
	/*@PutMapping("/producto/{id}")
	public Camaras editarProducto(@RequestBody Camaras editar, @PathVariable Long id) {
		
		
		if(camaraRepositorio.existsById(id)) {
			editar.setCameraID(id);
			return camaraRepositorio.save(editar);
		}else {
			return null; //Si no existe devolvemos null.
		}
		
	}*/

	/**
	 * Borra un producto del catálogo en base a su id
	 * @param id
	 * @return
	 * Devolvemos 204 not content
	 */
	/*@DeleteMapping("/producto/{id}")
	//El pathVariable saca el argumento id y lo inyecta en un argumento del metodo del controlador.
	public Camaras borrarProducto(@PathVariable Long id) {
		if(camaraRepositorio.existsById(id)) {
			Camaras producto= camaraRepositorio.findById(id).get();
			camaraRepositorio.deleteById(id);
			return producto;
		}else {
			return null; //Si no existe devolvemos null.
		}
	}*/

}
