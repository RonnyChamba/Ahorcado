package com.ideas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ideas.commons.ExceptionData;
import com.ideas.conf.Coneccion;
import com.ideas.entidades.Categoria;
import com.ideas.entidades.Palabra;

public class DAOPalabra {

	private Coneccion coneccion;

	public DAOPalabra() {
		coneccion = new Coneccion();
	}

	public boolean insert(Palabra entidad) {
		
		PreparedStatement pr = null;

		try {
			pr = coneccion.getConection().prepareStatement("INSERT INTO PALABRAS VALUES (NULL, ?,?,?)");
			pr.setString(1,  entidad.getNombre());
			pr.setString(2, entidad.getDescripcion());
			pr.setString(3, entidad.getCategoria().getIdCategoria());
			
			int  estado = pr.executeUpdate();
			if (estado != 0)
				return true;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error + " + e.getMessage());
		}
		return false;
	}

	public boolean isExiste(String parametro) throws ExceptionData{
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		try {
			
			pr = coneccion.getConection().prepareStatement("SELECT NOMBRE FROM CATEGORIAS WHERE NOMBRE LIKE ?");
			pr.setString(1, parametro);
			rs = pr.executeQuery();		
			return rs.next();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al acceder al consultar palabra: " + e.getMessage());
			 throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");
			
		}
		
	}
	
	public List<Palabra> listar (String filtro) throws ExceptionData{
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		List<Palabra> categorias = new ArrayList<Palabra>();
	
		try {

			pr = coneccion.getConection().prepareStatement("SELECT * FROM PALABRAS WHERE NOMBRE LIKE '%"+filtro +"%' ORDER BY NOMBRE");
			rs = pr.executeQuery();
			
			while (rs.next()) {
				// CODIGOCATEGORIA, NOMBRE, DESCRIPCION
				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getString("CODIGOCATEGORIA"));
				categoria.setNombre(rs.getString("NOMBRE"));
				categoria.setDescripcion(rs.getString("DESCRIPCION"));
				//categorias.add(categoria);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass().getSimpleName() +" Error al acceder al listar: " + e.getMessage() );
			 throw new ExceptionData("Error al consultar las categorias, intentalo mas tarde");
			
		}
		return categorias;
		
	}
	/**
	 * 
	 * @param parametro : El id de la categoria
	 * @return Si la categoria existe la retorna, caso contrario retorn null
	 * @throws ExceptionData
	 */
	public Categoria buscar(String parametro) throws ExceptionData{
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		Categoria categoria = null;
		try {
			
			pr = coneccion.getConection().prepareStatement("SELECT * FROM CATEGORIAS WHERE CODIGOCATEGORIA =?");
			pr.setString(1, parametro);
			rs = pr.executeQuery();
			if (rs.next()) {
				categoria = new Categoria();
				categoria.setIdCategoria(rs.getString("CODIGOCATEGORIA"));
				categoria.setNombre(rs.getString("NOMBRE"));
				categoria.setDescripcion(rs.getString("DESCRIPCION"));
			}
		
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al acceder al consultar categoria: " + e.getMessage());
			 throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");
			
		}
		
		return categoria;
		
	}
	
	
	/**
	 * 
	 * @param entidad 	Datos de la categoria que se desea Actualizar
	 * @return 
	 */
	public boolean update(Categoria entidad) {

		PreparedStatement pr = null;

		try {
			pr = coneccion.getConection().prepareStatement("UPDATE CATEGORIAS SET NOMBRE=?, DESCRIPCION =? WHERE CODIGOCATEGORIA=?");
			pr.setString(1,  entidad.getNombre());
			pr.setString(2, entidad.getDescripcion());
			pr.setString(3, entidad.getIdCategoria());
			int  estado = pr.executeUpdate();
			
			if (estado != 0)
				return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error + " + e.getMessage());
		}
		return false;
	}
	
	
	
	/**
	 * 
	 * @param id: id de la categoria a eliminar
	 * @return : true si elimina la categoria
	 */
	public boolean delete(String id) {

		PreparedStatement pr = null;

		try {
			pr = coneccion.getConection().prepareStatement("DELETE FROM CATEGORIAS WHERE CODIGOCATEGORIA=?");
			pr.setString(1, id);
			int  estado = pr.executeUpdate();
			
			if (estado != 0)
				return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error + " + e.getMessage());
		}
		return false;
	}
	

	/**
	 * 
	 * @return La ultima categoria Ingresada , caso contrario retorna null
	 * @throws ExceptionData : Si ocurre un error al consular la categoria
	 */
	public Categoria lastInsert() throws ExceptionData{
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		Categoria categoria = null;
		try {
			
			pr = coneccion.getConection().prepareStatement("SELECT * FROM CATEGORIAS WHERE CODIGOCATEGORIA = (SELECT MAX(CODIGOCATEGORIA) FROM CATEGORIAS)");
			rs = pr.executeQuery();
			if (rs.next()) {
				categoria = new Categoria();
				categoria.setIdCategoria(rs.getString("CODIGOCATEGORIA"));
				categoria.setNombre(rs.getString("NOMBRE"));
				categoria.setDescripcion(rs.getString("DESCRIPCION"));
			}		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al acceder al consultar categoria: " + e.getMessage());
			 throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");
			
		}
		
		return categoria;
		
	}

}
