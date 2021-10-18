package com.ideas.test;

import java.util.List;

import com.ideas.dao.DAOCategoria;
import com.ideas.entidades.Categoria;

public class TestCategoria {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TestCategoria.insert();
		// TestCategoria.isExiste();
		TestCategoria.listar();
	}

	public static void isExiste() {
		DAOCategoria daoCategoria = new DAOCategoria();

		try {
			System.out.println(daoCategoria.isExiste("leoN"));

		} catch (Exception e) {
			// TODO: handle exception
			// System.out.println(e.getMessage());
		}
	}

	public static void insert() {

		DAOCategoria daoCategoria = new DAOCategoria();

		Categoria categoria = new Categoria();
		categoria.setNombre("GALLINA");
		categoria.setDescripcion("PONE HUEVOS");

		boolean retorno = daoCategoria.insert(categoria);

		System.out.println(retorno);

	}

	public static void listar() {

		try {
			DAOCategoria daoCategoria = new DAOCategoria();
			String filtro = "";
			List<Categoria> categorias = daoCategoria.listar(filtro);
			System.out.println(categorias);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		


		

	}

}
