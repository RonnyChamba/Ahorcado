package com.ideas.entidades;

import java.util.List;

public class AdminCategorias {

	private List<Palabra> palabras;

	public void add(Palabra palabra) {

		palabras.add(palabra);
	}

	public Palabra buscar(String nombre) {		
		
		for (Palabra palabra : palabras) {

			if (palabra.getNombre().equalsIgnoreCase(nombre))
				return palabra;
		}
		return null;
		
		
		

	}
}
