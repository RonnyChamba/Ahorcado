package com.ideas.entidades;

import java.util.List;

public class Jugador {
	private String idJugador;
	private String tipo;
	private String cedula;
	private String nombre;
	private String ciudad;
	private String clave;
	private double puntaje;
	private List<Juego> juegos;
	
	public Jugador() {
	
		
		super();
	}
	
	public Jugador(String idJugador,String tipo, String cedula, String nombre, String ciudad, String clave, double puntaje) {
		super();
		this.idJugador = idJugador;
		this.tipo = tipo;
		this.cedula = cedula;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.clave = clave;
		this.puntaje = puntaje;
	}

	public String getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(String idJugador) {
		this.idJugador = idJugador;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public double getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(double puntaje) {
		this.puntaje = puntaje;
	}

	public List<Juego> getJuegos() {
		return juegos;
	}

	public void setJuegos(List<Juego> juegos) {
		this.juegos = juegos;
	}

	@Override
	public String toString() {
		return "Jugador [idJugador=" + idJugador + ", tipo=" + tipo + ", cedula=" + cedula + ", nombre=" + nombre
				+ ", ciudad=" + ciudad + ", clave=" + clave + ", puntaje=" + puntaje + ", juegos=" + juegos + "]";
	}


}
