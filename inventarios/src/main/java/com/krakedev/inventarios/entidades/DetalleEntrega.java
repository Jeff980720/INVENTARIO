package com.krakedev.inventarios.entidades;

public class DetalleEntrega {
	private int codigo;
	private Entrega cabecera;
	private Productos codProd;
	private int cantidad;
	private double precioVenta;
	private double subTotal;
	private double subTotalIVA;
	
	public DetalleEntrega() {}
	
	public DetalleEntrega(int codigo, Entrega cabecera, Productos codProd, int cantidad, double precioVenta,
			double subTotal, double subTotalIVA) {
		super();
		this.codigo = codigo;
		this.cabecera = cabecera;
		this.codProd = codProd;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.subTotal = subTotal;
		this.subTotalIVA = subTotalIVA;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Entrega getCabecera() {
		return cabecera;
	}

	public void setCabecera(Entrega cabecera) {
		this.cabecera = cabecera;
	}

	public Productos getCodProd() {
		return codProd;
	}

	public void setCodProd(Productos codProd) {
		this.codProd = codProd;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getSubTotalIVA() {
		return subTotalIVA;
	}

	public void setSubTotalIVA(double subTotalIVA) {
		this.subTotalIVA = subTotalIVA;
	}

	@Override
	public String toString() {
		return "DetalleEntrega [codigo=" + codigo + ", cabecera=" + cabecera + ", codProd=" + codProd + ", cantidad="
				+ cantidad + ", precioVenta=" + precioVenta + ", subTotal=" + subTotal + ", subTotalIVA=" + subTotalIVA
				+ "]";
	}
}
