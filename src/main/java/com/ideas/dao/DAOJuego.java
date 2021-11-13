package com.ideas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ideas.commons.ExceptionData;
import com.ideas.conf.Coneccion;
import com.ideas.entidades.Juego;
import com.ideas.entidades.Jugador;
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
			pr.setString(4,  entidad.getPalabra().getIdPalabra());
			pr.setString(5, entidad.getJugador().getIdJugador());
			
			return pr.executeUpdate()>0;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass().getSimpleName() +" Error al insertar: " + e.getMessage() );
			throw new ExceptionData("Error al insertar, no se guardo el juego");
		}
		
	}
	
	
	public List<Juego> listar(Jugador entidad, String limite) throws ExceptionData {
		PreparedStatement pr = null;
		ResultSet rs = null;
		 List<Juego> juegos = new ArrayList<Juego>();
		 limite =   limite ==null || limite.equalsIgnoreCase("")?"":"LIMIT "+ limite;

		try {

			pr = coneccion.getConection().prepareStatement("SELECT IDE_JUE, FEC_JUE, TIE_JUE, PUN_JUE, "
					+ "IDE_PAL, NOM_PAL, DES_PAL, "
					+ "IDE_CAT, NOM_CAT, DES_CAT "
					+ "FROM JUGADORES "
					+ "INNER JOIN JUEGOS  ON IDE_JUG  = FK_IDJUGADOR "
					+ "INNER JOIN PALABRAS ON IDE_PAL = FK_IDPALABRA "
					+ "INNER JOIN CATEGORIAS ON IDE_CAT = FK_CODIGOCATEGORIA "
					+ "WHERE  DNI_JUG=?  ORDER BY IDE_JUE DESC " + limite);
			pr.setString(1, entidad.getCedula());
			rs = pr.executeQuery();

			while (rs.next()) {		
				Juego juego = new Juego();
				
				juego.setIdJuego(rs.getString("IDE_JUE"));
				juego.setFecha(rs.getString("FEC_JUE"));
				juego.setTiempo(rs.getInt("TIE_JUE"));
				juego.setPuntaje(rs.getDouble("PUN_JUE"));
				juego.getPalabra().setIdPalabra(rs.getString("IDE_PAL"));
				juego.getPalabra().setNombre(rs.getString("NOM_PAL"));
				juego.getPalabra().setDescripcion(rs.getString("DES_PAL"));
				juego.getPalabra().getCategoria().setIdCategoria(rs.getString("IDE_CAT"));
				juego.getPalabra().getCategoria().setNombre(rs.getString("NOM_CAT"));
				juego.getPalabra().getCategoria().setDescripcion(rs.getString("DES_CAT"));
				juegos.add(juego);
				
								
			}
			

		} catch (Exception e) {
			System.out.println("Error al acceder al sistema");
			 throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");
		}

		return juegos;
	}

	
	public int numeroJuegos(Jugador entidad) throws ExceptionData {
		PreparedStatement pr = null;
		ResultSet rs = null;
		try {

			pr = coneccion.getConection().prepareStatement("SELECT COUNT(IDE_JUE) AS NUMEROS_JUEGOS "
					+ "FROM JUGADORES "
					+ "INNER JOIN JUEGOS  ON IDE_JUG  = FK_IDJUGADOR "
					+ "WHERE  DNI_JUG=?");
			pr.setString(1,entidad.getCedula());
			rs = pr.executeQuery();

			if (rs.next()) return rs.getInt("NUMEROS_JUEGOS");
			else return -1;
			

		} catch (Exception e) {
			System.out.println("Error al acceder al sistema");
			 throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");
		}

	}
	
		
}
