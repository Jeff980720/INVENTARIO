package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.Productos;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("productos")
public class ServiciosProductos {
	@Path("buscar/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar (@PathParam("sub") String subcadena) {
		ProveedoresBDD provBDD = new ProveedoresBDD();
		ArrayList<Productos> productos = null;
		try {
			productos = provBDD.buscarProductos(subcadena);
			return Response.ok(productos).build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertar(Productos producto) {
		System.out.println(">>>>>>>" + producto);
		ProveedoresBDD product = new ProveedoresBDD();
		try {
			product.crearProducto(producto);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Productos producto) {
		System.out.println("Actualizando Cliente" + producto);
		ProveedoresBDD actProd = new ProveedoresBDD();
		try {
			actProd.modificarProducto(producto);;
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("buscarconcodigo/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar (@PathParam("sub") int subcadena) {
		ProveedoresBDD provBDD = new ProveedoresBDD();
		ArrayList<Productos> productos = null;
		try {
			productos = provBDD.buscarProductoPorIdentificador(subcadena);
			return Response.ok(productos).build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
