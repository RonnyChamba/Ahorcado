package com.ideas.test;

import com.ideas.dao.DAOJugador;
import com.ideas.entidades.Jugador;

public class TestJugador {

	
	public static void main (String ...rene) {
		
		
		DAOJugador dao = new DAOJugador();
		Jugador juador = new Jugador();
		juador.setCedula("1723774640");
		juador.setNombre("Rene");
		juador.setCiudad("Santo Domingo");
		juador.setClave("123");
		juador.setPuntaje(0);
		
	System.out.println(dao.insert(juador));
	}
}
