package com.ideas.entidades;

import java.util.ArrayList;
import java.util.List;

public class Categoria {

	private String idCategoria;
	private String nombre;
	private String descripcion;
	private List<Palabra> palabras;
	

	public Categoria() {
		super();
		palabras = new ArrayList<Palabra>();
	}

	public Categoria(String idCategoria, String nombre, String descripcion) {
		super();
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		palabras = new ArrayList<Palabra>();

	}

	public String getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
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

	public List<Palabra> getPalabras() {
		return palabras;
	}

	public void setPalabras(List<Palabra> palabras) {
		this.palabras = palabras;
	}

	@Override
	public String toString() {
		
		return "Categoria [idCategoria=" + idCategoria + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
		//return "\""+nombre+"\" : {\"id\" : "+idCategoria+", \"name\" : \""+nombre+"\", \"descripcion\": \""+descripcion+"\"}";
		
	}
	
}
