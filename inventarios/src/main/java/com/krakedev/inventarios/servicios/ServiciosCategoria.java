package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("categoria")
public class ServiciosCategoria {
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertar(Categoria categoria) {
		System.out.println(">>>>>>>" + categoria);
		ProveedoresBDD cat = new ProveedoresBDD();
		try {
			cat.crearCategoria(categoria);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Categoria categoria) {
		System.out.println("Actualizando Cliente" + categoria);
		ProveedoresBDD actCat = new ProveedoresBDD();
		try {
			actCat.actualizarCategoria(categoria);
			;
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("buscar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar() {
		ProveedoresBDD catBDD = new ProveedoresBDD();
		ArrayList<Categoria> categoria = null;
		try {
			categoria = catBDD.recuperarCategoria();
			return Response.ok(categoria).build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
