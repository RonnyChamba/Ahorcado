package com.ideas.conf;

import java.sql.Connection;
import java.sql.DriverManager;

public class Coneccion {	
	private static final String USER ="root";
	private static final String PASS ="Losmaspepas2018";
	private static final String DATA_BASE ="JUEGO_AHORCADO";
	private static final String URL ="jdbc:mysql://localhost:3306/"+DATA_BASE+"?serverTimezone=UTC";
	private static final String URL_DRIVER ="com.mysql.cj.jdbc.Driver";
	private Connection cn = null;

	public Coneccion() {
		
	}
	
	public Connection getConection () {
		
		try {
		
			Class.forName(URL_DRIVER);
			cn = DriverManager.getConnection(URL, USER, PASS);
			
			// System.out.println("exito");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e.getMessage());
		}
		
		return cn;
	}
	
	public void closeConection() {
		
		
		try {
		
			if (cn !=null) {
				cn.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al desconectar " + e.getMessage());
		}
	}
	
	

}
