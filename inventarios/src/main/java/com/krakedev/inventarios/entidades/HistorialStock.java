package com.krakedev.inventarios.entidades;

import java.util.Date;

public class HistorialStock {
	private int codigo;
	private Date fecha;
	private String referencia;
	private Productos codigoProduct;
	private int cantidad;
	
	public HistorialStock() {}
	
	public HistorialStock(int codigo, Date fecha, String referencia, Productos codigoProduct, int cantidad) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		this.referencia = referencia;
		this.codigoProduct = codigoProduct;
		this.cantidad = cantidad;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Productos getCodigoProduct() {
		return codigoProduct;
	}

	public void setCodigoProduct(Productos codigoProduct) {
		this.codigoProduct = codigoProduct;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "HistorialStock [codigo=" + codigo + ", fecha=" + fecha + ", referencia=" + referencia
				+ ", codigoProduct=" + codigoProduct + ", cantidad=" + cantidad + "]";
	}
}
