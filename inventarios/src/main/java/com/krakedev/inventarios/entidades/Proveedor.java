package com.krakedev.inventarios.entidades;

public class Proveedor {
	private String identificador;
	private TipoDocumento tipoDocumento;
	private String nombre;
	private String teledfono;
	private String correo;
	private String direccion;
	
	public Proveedor() {}

	public Proveedor(String identificador) {
		super();
		this.identificador = identificador;
	}

	public Proveedor(String identificador, TipoDocumento tipoDocumento, String nombre, String teledfono, String correo,
			String direccion) {
		super();
		this.identificador = identificador;
		this.tipoDocumento = tipoDocumento;
		this.nombre = nombre;
		this.teledfono = teledfono;
		this.correo = correo;
		this.direccion = direccion;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTeledfono() {
		return teledfono;
	}

	public void setTeledfono(String teledfono) {
		this.teledfono = teledfono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Proveedor [identificador=" + identificador + ", tipoDocumento=" + tipoDocumento + ", nombre=" + nombre
				+ ", teledfono=" + teledfono + ", correo=" + correo + ", direccion=" + direccion + "]";
	}
	
}
