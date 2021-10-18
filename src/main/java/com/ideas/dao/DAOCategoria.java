package com.ideas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ideas.commons.ExceptionData;
import com.ideas.conf.Coneccion;
import com.ideas.entidades.Categoria;

public class DAOCategoria {

	private Coneccion coneccion;

	public DAOCategoria() {
		coneccion = new Coneccion();
	}

	public boolean insert(Categoria entidad) {

		PreparedStatement pr = null;

		try {
			pr = coneccion.getConection().prepareStatement("INSERT INTO CATEGORIAS VALUES (NULL, ?,?)");
			pr.setString(1, entidad.getNombre());
			pr.setString(2, entidad.getDescripcion());

			int estado = pr.executeUpdate();
			if (estado != 0)
				return true;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error + " + e.getMessage());
		}
		return false;
	}

	public boolean isExiste(String parametro) throws ExceptionData {

		PreparedStatement pr = null;
		ResultSet rs = null;
		try {

			pr = coneccion.getConection().prepareStatement("SELECT NOM_CAT FROM CATEGORIAS WHERE NOM_CAT LIKE ?");
			pr.setString(1, parametro);
			rs = pr.executeQuery();
			return rs.next();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al acceder al consultar palabra: " + e.getMessage());
			throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");

		}

	}

	public List<Categoria> listar(String filtro) throws ExceptionData {

		PreparedStatement pr = null;
		ResultSet rs = null;
		List<Categoria> categorias = new ArrayList<Categoria>();

		try {

			pr = coneccion.getConection().prepareStatement(
					"SELECT * FROM CATEGORIAS WHERE NOM_CAT LIKE '%" + filtro + "%' ORDER BY IDE_CAT");
			rs = pr.executeQuery();

			while (rs.next()) {
				// CODIGOCATEGORIA, NOMBRE, DESCRIPCION
				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getString("IDE_CAT"));
				categoria.setNombre(rs.getString("NOM_CAT"));
				categoria.setDescripcion(rs.getString("DES_CAT"));
				categorias.add(categoria);

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass().getSimpleName() + " Error al acceder al listar: " + e.getMessage());
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
	public Categoria buscar(String parametro) throws ExceptionData {

		PreparedStatement pr = null;
		ResultSet rs = null;
		Categoria categoria = null;
		try {

			pr = coneccion.getConection().prepareStatement("SELECT * FROM CATEGORIAS WHERE IDE_CAT =?");
			pr.setString(1, parametro);
			rs = pr.executeQuery();
			if (rs.next()) {
				categoria = new Categoria();
				categoria.setIdCategoria(rs.getString("IDE_CAT"));
				categoria.setNombre(rs.getString("NOM_CAT"));
				categoria.setDescripcion(rs.getString("DES_CAT"));
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
	 * @param entidad Datos de la categoria que se desea Actualizar
	 * @return
	 */
	public boolean update(Categoria entidad) {

		PreparedStatement pr = null;

		try {
			pr = coneccion.getConection()
					.prepareStatement("UPDATE CATEGORIAS SET NOM_CAT=?, DES_CAT =? WHERE IDE_CAT=?");
			pr.setString(1, entidad.getNombre());
			pr.setString(2, entidad.getDescripcion());
			pr.setString(3, entidad.getIdCategoria());
			int estado = pr.executeUpdate();

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
			pr = coneccion.getConection().prepareStatement("DELETE FROM CATEGORIAS WHERE IDE_CAT=?");
			pr.setString(1, id);
			int estado = pr.executeUpdate();

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
	public Categoria lastInsert() throws ExceptionData {

		PreparedStatement pr = null;
		ResultSet rs = null;
		Categoria categoria = null;
		try {

			pr = coneccion.getConection()
					.prepareStatement("SELECT * FROM CATEGORIAS WHERE IDE_CAT = (SELECT MAX(IDE_CAT) FROM CATEGORIAS)");
			rs = pr.executeQuery();
			if (rs.next()) {
				categoria = new Categoria();
				categoria.setIdCategoria(rs.getString("IDE_CAT"));
				categoria.setNombre(rs.getString("NOM_CAT"));
				categoria.setDescripcion(rs.getString("DES_CAT"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al acceder al consultar categoria: " + e.getMessage());
			throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");

		}

		return categoria;

	}

}
