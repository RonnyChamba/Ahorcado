package com.ideas.test;

import java.util.List;

import com.ideas.commons.ExceptionData;
import com.ideas.dao.DAOJuego;
import com.ideas.dao.DAOJugador;
import com.ideas.entidades.Juego;
import com.ideas.entidades.Jugador;
import com.ideas.entidades.Palabra;

public class TestJuego {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TestJuego.getPalabra("3");
		TestJuego.getJuegos();
	}

	static void getPalabra(String idCategoria) {

		try {

			DAOJuego daoJuego = new DAOJuego();
			Palabra palabra = daoJuego.getPalabraJuego(idCategoria);
			System.out.println(palabra);

		} catch (ExceptionData e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

	}

	static void getJuegos() {

		try {

			
			
			String dni = "1723774640";
			String password = "123";
			Jugador jugador = new DAOJugador().login(dni, password);
			
			/*DAOJuego daoJuego = new DAOJuego();
			List<Juego> juegos = daoJuego.listar(jugador, null);
			
			juegos.forEach( element -> System.out.println(element));
			//System.out.println(juegos);
		
		*/
			
			jugador.getJuegos().forEach(element -> System.out.println(element));
		} catch (ExceptionData e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

	}

}
