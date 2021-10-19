package com.ideas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.ideas.commons.ExceptionData;
import com.ideas.conf.Coneccion;
import com.ideas.entidades.Palabra;

public class DAOPalabra {

	private Coneccion coneccion;

	public DAOPalabra() {
		coneccion = new Coneccion();
	}

	public boolean insert(Palabra entidad)  throws ExceptionData{
		
		PreparedStatement pr = null;

		try {
			pr = coneccion.getConection().prepareStatement("INSERT INTO PALABRAS VALUES (NULL, ?,?,?)");
			pr.setString(1,  entidad.getNombre());
			pr.setString(2, entidad.getDescripcion());
			pr.setString(3, entidad.getCategoria().getIdCategoria());	
			return pr.executeUpdate()>0;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error + " + e.getMessage());
			throw new ExceptionData("Error al insertar, intentalo mas tarde");
		}
		
	}

	public boolean isExiste(String parametro) throws ExceptionData{
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		try {
			
			pr = coneccion.getConection().prepareStatement("SELECT NOM_PAL FROM PALABRAS WHERE NOM_PAL LIKE ?");
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
		List<Palabra> palabras = new ArrayList<Palabra>();
	
		try {

			pr = coneccion.getConection().prepareStatement("SELECT  IDE_PAL, NOM_PAL, DES_PAL,"
					+ "IDE_CAT, NOM_CAT, DES_CAT "
					+ "FROM PALABRAS "
					+ "INNER JOIN CATEGORIAS ON IDE_CAT = FK_CODIGOCATEGORIA "
					+ "WHERE NOM_PAL LIKE '%"+filtro+"%' "
					+ "ORDER BY  NOM_PAL");
			rs = pr.executeQuery();
			
			while (rs.next()) {
				// IDE_PAL, NOM_PAL, DES_PAL, FK_CODIGOCATEGORIA
				Palabra palabra = new Palabra();
				palabra.setIdPalabra(rs.getString("IDE_PAL"));
				palabra.setNombre(rs.getString("NOM_PAL"));
				palabra.setDescripcion(rs.getString("DES_PAL"));
				palabra.getCategoria().setIdCategoria(rs.getString("IDE_CAT"));
				palabra.getCategoria().setNombre(rs.getString("NOM_CAT"));
				palabra.getCategoria().setDescripcion(rs.getString("DES_CAT"));
				palabras.add(palabra);		
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass().getSimpleName() +" Error al acceder al listar: " + e.getMessage() );
			 throw new ExceptionData("Error al consultar, intentalo mas tarde");
			
		}
		return palabras;
		
	}
	/**
	 * 
	 * @param parametro : El id de la palabra
	 * @return Si la palabra existe la retorna, caso contrario retorn null
	 * @throws ExceptionData
	 */
	public Palabra buscar(String parametro) throws ExceptionData{
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		Palabra palabra = null;
		try {
			
			pr = coneccion.getConection().prepareStatement("SELECT  IDE_PAL, NOM_PAL, DES_PAL,"
					+ "IDE_CAT, NOM_CAT, DES_CAT "
					+ "FROM PALABRAS "
					+ "INNER JOIN CATEGORIAS ON IDE_CAT = FK_CODIGOCATEGORIA "
					+ "WHERE IDE_PAL =?");
			pr.setString(1, parametro);
			rs = pr.executeQuery();
			if (rs.next()) {		
				// IDE_PAL, NOM_PAL, DES_PAL, FK_CODIGOCATEGORIA
				palabra = new Palabra();
				palabra.setIdPalabra(rs.getString("IDE_PAL"));
				palabra.setNombre(rs.getString("NOM_PAL"));
				palabra.setDescripcion(rs.getString("DES_PAL"));
				palabra.getCategoria().setIdCategoria(rs.getString("IDE_CAT"));
				palabra.getCategoria().setNombre(rs.getString("NOM_CAT"));
				palabra.getCategoria().setDescripcion(rs.getString("DES_CAT"));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.println(this.getClass().getSimpleName() +" Error al buscar palabra: " + e.getMessage() );
			 throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");
			
		}
		
		return palabra;
		
	}
	
	
	/**
	 * 
	 * @param entidad 	Datos de la categoria que se desea Actualizar
	 * @return 
	 */
	public boolean update(Palabra entidad) throws ExceptionData {

		PreparedStatement pr = null;

		try {
			pr = coneccion.getConection().prepareStatement("UPDATE PALABRAS SET NOM_PAL=?, DES_PAL =?, FK_CODIGOCATEGORIA =? WHERE IDE_PAL=?");
			pr.setString(1,  entidad.getNombre());
			pr.setString(2, entidad.getDescripcion());
			pr.setString(3, entidad.getCategoria().getIdCategoria());
			pr.setString(4, entidad.getIdPalabra());
			return pr.executeUpdate()>0;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass().getSimpleName() +" Error al actualizar: " + e.getMessage() );
			throw new ExceptionData("Error al actulizar, intentalo mas tarde");
		}
		
	}
	
	
	/**
	 * 
	 * @param id el id de la palabra
	 * @return true: registro elminado. false 0 registros eliminado
	 * @throws ExceptionData : Si ocurre un erro
	 */
	public boolean delete(String id)  throws ExceptionData{

		PreparedStatement pr = null;
		try {
			pr = coneccion.getConection().prepareStatement("DELETE FROM PALABRAS WHERE IDE_PAL=?");
			pr.setString(1, id);
			return  pr.executeUpdate()>0;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass().getSimpleName() +" Error al eliminar: " + e.getMessage() );			
			throw new ExceptionData("Error al eliminar, intentalo mas tarde");
		}
		
	}

	/**
	 * 
	 * @return La ultima palabra Ingresada , caso contrario retorna null
	 * @throws ExceptionData : Si ocurre un error al consular la palabra
	 */
	public Palabra lastInsert() throws ExceptionData{
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		Palabra palabra = null;
		try {
			
			pr = coneccion.getConection().prepareStatement("SELECT  IDE_PAL, NOM_PAL, DES_PAL,"
					+ "IDE_CAT, NOM_CAT, DES_CAT "
					+ "FROM PALABRAS "
					+ "INNER JOIN CATEGORIAS ON IDE_CAT = FK_CODIGOCATEGORIA "
					+ "WHERE IDE_PAL = (SELECT MAX(IDE_PAL) FROM PALABRAS)");
			rs = pr.executeQuery();
			if (rs.next()) {
				
				// IDE_PAL, NOM_PAL, DES_PAL, FK_CODIGOCATEGORIA
				palabra = new Palabra();
				palabra.setIdPalabra(rs.getString("IDE_PAL"));
				palabra.setNombre(rs.getString("NOM_PAL"));
				palabra.setDescripcion(rs.getString("DES_PAL"));
				palabra.getCategoria().setIdCategoria(rs.getString("IDE_CAT"));
				palabra.getCategoria().setNombre(rs.getString("NOM_CAT"));
				palabra.getCategoria().setDescripcion(rs.getString("DES_CAT"));
						
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass().getSimpleName() +" Error al consulta  ultima palabra: " + e.getMessage() );
			 throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");		
		}
		
		return palabra;	
	}

}
