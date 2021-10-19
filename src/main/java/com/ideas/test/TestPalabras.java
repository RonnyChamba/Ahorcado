package com.ideas.test;

import java.util.List;

import com.ideas.commons.ExceptionData;
import com.ideas.dao.DAOPalabra;
import com.ideas.entidades.Palabra;

public class TestPalabras {

	public static void main(String[] args) {
		
		
		// TestPalabras.insert();
		// TestPalabras.isExist();
		// TestPalabras.listar("");
		// TestPalabras.buscar("1");
		// TestPalabras.update();
		// TestPalabras.delete("2");
		// TestPalabras.lasInsert();
	}

	static void insert() {

		DAOPalabra daoPalabra = new DAOPalabra();

		Palabra palabra = new Palabra();
		palabra.setNombre("PERA");
		palabra.setDescripcion("Para Saborear");
		palabra.getCategoria().setIdCategoria("3");

		try {
			System.out.println(daoPalabra.insert(palabra));
		} catch (ExceptionData e) {
			// TODO Auto-generated catch block
			
			System.out.println(e.getMessage());
		}

	}
	
	static void isExist() {

		DAOPalabra daoPalabra = new DAOPalabra();
		try {
			System.out.println(daoPalabra.isExiste("Elefantes"));
		} catch (ExceptionData e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	static void listar(String filtro) {

		DAOPalabra daoPalabra = new DAOPalabra();
		try {
			
			List<Palabra> palabras = daoPalabra.listar(filtro);
			System.out.println(palabras);
			
		} catch (ExceptionData e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	static void buscar(String filtro) {

		DAOPalabra daoPalabra = new DAOPalabra();
		try {
			
			Palabra palabra = daoPalabra.buscar(filtro);
			System.out.println(palabra);
			
		} catch (ExceptionData e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	static void update() {

		DAOPalabra daoPalabra = new DAOPalabra();
		Palabra palabra = new Palabra();
		palabra.setIdPalabra("3");
		palabra.setNombre("MANZ");
		palabra.setDescripcion("Color verde");
		palabra.getCategoria().setIdCategoria("3");
		try {
			System.out.println(daoPalabra.update(palabra));
		} catch (ExceptionData e) {
			// TODO Auto-generated catch block
		
			System.out.println(e.getMessage());
		}

	}
	
	static void delete(String id) {

		DAOPalabra daoPalabra = new DAOPalabra();
		try {
			boolean estado = daoPalabra.delete(id);	
			String mensaje = estado? "Registro eliminado": "0 Registro eliminados";
			System.out.println(mensaje);
		} catch (ExceptionData e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	static void lasInsert() {

		DAOPalabra daoPalabra = new DAOPalabra();
		try {
			Palabra palabra = daoPalabra.lastInsert();
			
			System.out.println(palabra);
		} catch (ExceptionData e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}
