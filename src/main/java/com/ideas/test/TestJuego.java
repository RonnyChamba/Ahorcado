package com.ideas.test;

import com.ideas.commons.ExceptionData;
import com.ideas.dao.DAOJuego;
import com.ideas.entidades.Palabra;

public class TestJuego {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TestJuego.getPalabra("3");
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

}
