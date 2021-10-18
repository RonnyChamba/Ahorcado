package com.ideas.entidades;

public class Palabra {
	private String idPalabra;
	private String nombre;
	private String descripcion;
	private Categoria categoria;
	
	public Palabra() {
		categoria =  new Categoria();
	}

	public Palabra(String idPalabra, String nombre, String descripcion, Categoria categoria) {
		this.idPalabra = idPalabra;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.categoria = categoria;
	}

	public String getIdPalabra() {
		return idPalabra;
	}

	public void setIdPalabra(String idPalabra) {
		this.idPalabra = idPalabra;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Palabra [idPalabra=" + idPalabra + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", categoria=" + categoria + "]";
	}
	
	
	
	
}
