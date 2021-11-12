package com.ideas.entidades;

import java.util.List;

public class Juego {

	
	private String idJuego;
	private String fecha;
	private int tiempo;
	private double puntaje;
	private Palabra palabra;
	private Jugador jugador;
	
	public Juego() {
		palabra = new Palabra();
	}
	
	public Juego(String idJuego, String fecha, int tiempo, double puntaje ) {
		super();
		this.idJuego = idJuego;
		this.fecha = fecha;
		this.tiempo = tiempo;
		this.puntaje = puntaje;
	}

	public String getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(String idJuego) {
		this.idJuego = idJuego;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public double getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(double puntaje) {
		this.puntaje = puntaje;
	}

	public Palabra getPalabra() {
		return palabra;
	}

	public void setPalabra(Palabra palabra) {
		this.palabra = palabra;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	@Override
	public String toString() {
		return "Juego [idJuego=" + idJuego + ", fecha=" + fecha + ", tiempo=" + tiempo + ", puntaje=" + puntaje
				+ ", palabras=" + palabra + ", jugador=" + jugador + "]";
	}
	
	
}

	