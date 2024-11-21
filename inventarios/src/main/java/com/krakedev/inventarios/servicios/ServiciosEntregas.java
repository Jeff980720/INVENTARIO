package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.Entrega;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("ventas")
public class ServiciosEntregas {
	@Path("guardar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertar(Entrega entregas) {
		System.out.println(">>>>>>>" + entregas);
		ProveedoresBDD entrega = new ProveedoresBDD();
		try {
			entrega.insertarEntrega(entregas);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
}
