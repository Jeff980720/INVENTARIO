package com.krakedev.inventarios.entidades;

import java.util.ArrayList;
import java.util.Date;

public class Entrega {
	private int codigo;
	private Date fecha;
	private double totalSinIVA=0;
	private double iva=0;
	private double total=0;
	private ArrayList<DetalleEntrega> detalles;
	private ArrayList<Productos> ivaVent;
	
	public Entrega() {}
	
	public Entrega(int codigo, Date fecha, double totalSinIVA, double iva, double total) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		this.totalSinIVA = totalSinIVA;
		this.iva = iva;
		this.total = total;
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

	public double getTotalSinIVA() {
		return totalSinIVA;
	}

	public void setTotalSinIVA(double totalSinIVA) {
		this.totalSinIVA = totalSinIVA;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public ArrayList<DetalleEntrega> getDetalles() {
		return detalles;
	}

	public void setDetalles(ArrayList<DetalleEntrega> detalles) {
		this.detalles = detalles;
	}
	
	public ArrayList<Productos> getIvaVent() {
		return ivaVent;
	}

	public void setIvaVent(ArrayList<Productos> ivaVent) {
		this.ivaVent = ivaVent;
	}

	@Override
	public String toString() {
		return "Entrega [codigo=" + codigo + ", fecha=" + fecha + ", totalSinIVA=" + totalSinIVA + ", iva=" + iva
				+ ", total=" + total + "]";
	}
}
