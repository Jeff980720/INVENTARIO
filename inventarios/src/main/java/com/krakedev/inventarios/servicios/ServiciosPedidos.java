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
import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("pedidos")
public class ServiciosPedidos {
	@Path("registrar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertar(Pedido pedidos) {
		System.out.println(">>>>>>>" + pedidos);
		ProveedoresBDD pedido = new ProveedoresBDD();
		try {
			pedido.insertarPedido(pedidos);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("recibir")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Pedido pedidos) {
		System.out.println("Actualizando pedido" + pedidos);
		ProveedoresBDD actPedido = new ProveedoresBDD();
		try {
			actPedido.modificarPedido(pedidos);
			return Response.ok().build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("buscar/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar (@PathParam("sub") String subcadena) {
		ProveedoresBDD provBDD = new ProveedoresBDD();
		ArrayList<DetallePedido> pedidos = null;
		try {
			pedidos = provBDD.buscarPedidoPorProveedor(subcadena);
			return Response.ok(pedidos).build();
		} catch (KrakedevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
