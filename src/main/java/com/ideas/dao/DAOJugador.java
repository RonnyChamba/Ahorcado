package com.ideas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.ideas.commons.ExceptionData;
import com.ideas.conf.Coneccion;
import com.ideas.entidades.Juego;
import com.ideas.entidades.Jugador;

public class DAOJugador {

	private Coneccion coneccion;

	public DAOJugador() {
		coneccion = new Coneccion();
	}

	public boolean insert(Jugador entidad) {

		PreparedStatement pr = null;

		try {
			pr = coneccion.getConection().prepareStatement("INSERT INTO JUGADORES VALUES (NULL, ?,?,?,?,?,?)");
			pr.setString(1, entidad.getTipo());
			pr.setString(2, entidad.getCedula());
			pr.setString(3, entidad.getNombre());
			pr.setString(4, entidad.getCiudad());
			pr.setString(5, entidad.getClave());
			pr.setDouble(6, entidad.getPuntaje());
			
			int  estado = pr.executeUpdate();
			if (estado != 0)
				return true;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error + " + e.getMessage());
		}
		return false;
	}

	public Jugador login(String dni, String pass) throws ExceptionData {
		PreparedStatement pr = null;
		ResultSet rs = null;
		Jugador jugador = null;

		try {

			pr = coneccion.getConection().prepareStatement("SELECT * FROM JUGADORES WHERE DNI_JUG =? AND CLA_JUG =?");
			pr.setString(1, dni);
			pr.setString(2, pass);
			rs = pr.executeQuery();

			if (rs.next()) {
				jugador = new Jugador();
				
				jugador.setIdJugador(String.valueOf(rs.getInt("IDE_JUG")));
				jugador.setTipo(rs.getString("TIP_JUG"));
				jugador.setCedula(rs.getString("DNI_JUG"));
				jugador.setNombre(rs.getString("NOM_JUG"));
				jugador.setCiudad(rs.getString("CIU_JUG"));
				jugador.setClave(rs.getString("CLA_JUG"));
				jugador.setPuntaje(rs.getDouble("PUN_JUG"));
				
				DAOJuego daoJuego = new DAOJuego();
				List<Juego> juegos = daoJuego.listar(jugador, "3");
				jugador.setJuegos(juegos);
				
			}
			

		} catch (Exception e) {
			System.out.println("Error al acceder al sistema");
			 throw new ExceptionData("Error al acceder al sistema, intentalo mas tarde");
		}

		return jugador;
	}

	public boolean isExiste(String dni) throws ExceptionData{
		
		PreparedStatement pr = null;
		ResultSet rs = null;
		try {
			
			
			pr = coneccion.getConection().prepareStatement("SELECT DNI_JUG FROM JUGADORES WHERE DNI_JUG=?");
			pr.setString(1, dni);
			rs = pr.executeQuery();		
			return rs.next();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al acceder al consultar jugador");
			 throw new ExceptionData("Error al verificar dni, intentalo mas tarde");
			
		}
		
	}
}
