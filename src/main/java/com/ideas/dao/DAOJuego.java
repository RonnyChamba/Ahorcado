package com.ideas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ideas.commons.ExceptionData;
import com.ideas.conf.Coneccion;
import com.ideas.entidades.Juego;
import com.ideas.entidades.Palabra;

public class DAOJuego {
	private Coneccion coneccion;
	
	public DAOJuego() {
		coneccion = new Coneccion();
	}
	
	
	public Palabra getPalabraJuego(String idCategoria) throws ExceptionData{
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		Palabra palabra = null;
		try {
			
			pr = coneccion.getConection().prepareStatement("SELECT  IDE_PAL, NOM_PAL, DES_PAL,"
					+ "IDE_CAT, NOM_CAT, DES_CAT "
					+ "FROM PALABRAS "
					+ "INNER JOIN CATEGORIAS ON IDE_CAT = FK_CODIGOCATEGORIA "
					+ "WHERE IDE_CAT=? ORDER BY RAND() LIMIT 1");
			pr.setString(1, idCategoria);
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

	
	public boolean insert(Juego entidad)  throws ExceptionData{
		
		PreparedStatement pr = null;

		try {
			pr = coneccion.getConection().prepareStatement("INSERT INTO JUEGOS VALUES (NULL, ?,?,?,?,?)");
			pr.setString(1,  entidad.getFecha());
			pr.setInt(2, entidad.getTiempo());
			pr.setDouble(3, entidad.getPuntaje());
			pr.setString(4,  entidad.getPalabras().get(0).getIdPalabra());
			pr.setString(5, entidad.getJugador().getIdJugador());
			
			return pr.executeUpdate()>0;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass().getSimpleName() +" Error al insertar: " + e.getMessage() );
			throw new ExceptionData("Error al insertar, no se guardo el juego");
		}
		
	}
	
		
}
