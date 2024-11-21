package com.krakedev.inventarios.entidades;

public class UnidadesMedida {
	private String codigo;
	private String descripcion;
	private CategoriaUDM categoriaunidadmedida;
	
	public UnidadesMedida() {}
	
	public UnidadesMedida(String codigo, String descripcion, CategoriaUDM categoriaunidadmedida) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.categoriaunidadmedida = categoriaunidadmedida;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CategoriaUDM getCategoriaunidadmedida() {
		return categoriaunidadmedida;
	}

	public void setCategoriaunidadmedida(CategoriaUDM categoriaunidadmedida) {
		this.categoriaunidadmedida = categoriaunidadmedida;
	}

	@Override
	public String toString() {
		return "UnidadesMedida [codigo=" + codigo + ", descripcion=" + descripcion + ", categoriaunidadmedida="
				+ categoriaunidadmedida + "]";
	}
}
