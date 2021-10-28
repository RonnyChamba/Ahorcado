package com.ideas.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.ideas.commons.ExceptionData;
import com.ideas.dao.DAOCategoria;
import com.ideas.dao.DAOJuego;
import com.ideas.entidades.Categoria;
import com.ideas.entidades.Palabra;
import com.ideas.utilidades.UtilidadesCategoria;
import com.ideas.utilidades.UtilidadesPalabra;

public class ControlJuego extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOCategoria daoCategoria;
	private DAOJuego daoJuego;
       
  
    public ControlJuego() {
        super();
        // TODO Auto-generated constructor stub
    	daoCategoria = new DAOCategoria();
    	daoJuego = new DAOJuego();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// System.out.println("Action:" + request.getParameter("action"));
		String action = request.getParameter("action").toLowerCase();
		switch (action) {
		case "jugar":
			mostrarForm(request, response, "jugar.jsp");
			break;
		case "listarcategorias":
			listarCategorias(request, response);
			break;
		case "datospalabra":
			getPalabraCategoria(request, response);
			break;

		default:
			break;
		}

	}
	
	private void mostrarForm(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {

		request.getRequestDispatcher(path).forward(request, response);

	}
	
	
	private void listarCategorias(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject();
		
		try {
			List<Categoria> categorias = daoCategoria.listar("");
			
			json.put("estado", true);
			json.put("categorias", UtilidadesCategoria.jsonStringList(categorias));
		
		} catch (ExceptionData e) {
			
			json.put("estado", false);
			json.put("sms", e.getMessage());
			
		}
		
		response.getWriter().print(json.toJSONString());
	}

	private void getPalabraCategoria(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject();
		
		try {
			
			String idCategoria = request.getParameter("idCategoria");
			Palabra palabra = daoJuego.getPalabraJuego(idCategoria);
			boolean estado = palabra !=null;
			json.put("estado", estado);
			if (estado) 				
				json.put("palabra", UtilidadesPalabra.jsonString(palabra));
			else json.put("sms", "No existe palabras disponibles para la categoria");
		} catch (ExceptionData e) {
			
			json.put("estado", false);
			json.put("sms", e.getMessage());
			
		}		
		response.getWriter().print(json.toJSONString());
	}
}
