package com.krakedev.inventarios.entidades;

public class EstadoPedido {
	private String estado;
	private String descripcion;
	
	public EstadoPedido() {}
	
	public EstadoPedido(String estado, String descripcion) {
		super();
		this.estado = estado;
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "EstadoPedido [estado=" + estado + ", descripcion=" + descripcion + "]";
	}
}
