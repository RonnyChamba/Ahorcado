package com.ideas.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import com.ideas.commons.ExceptionData;
import com.ideas.dao.DAOCategoria;
import com.ideas.dao.DAOPalabra;
import com.ideas.entidades.Categoria;
import com.ideas.entidades.Palabra;
import com.ideas.utilidades.UtilidadesCategoria;
import com.ideas.utilidades.UtilidadesPalabra;

// @WebServlet(description = "Manejador de Palabras", urlPatterns = { "/CPalabra" })
public class ControlPalabra extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOCategoria daoCategoria;
	private DAOPalabra daoPalabra;

	public ControlPalabra() {

		super();
		daoCategoria = new DAOCategoria();
		daoPalabra = new DAOPalabra();

	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// System.out.println("Action Servelet:" + request.getParameter("action"));
		String action = request.getParameter("action").toLowerCase();
		switch (action) {
			
		case "formpalabra":
			formPalabra(request, response);

			break;

		case "insertpalabra":
			insertPalabra(request, response);

			break;
		case "formupdatepalabra":
			formUpdatePalabra(request, response);

			break;
		case "actionupdatepalabra":
			actionUpdatePalabra(request, response);

			break;
		case "actiondeletepalabra":
			actionDeletePalabra(request, response);

			break;
		case "actionbuscarpalabra":
			actionBuscarPalabra(request, response);

			break;
		case "ultimapalabra":
			ultimaPalabraInsertada(request, response);

			break;

		default:
			break;
		}

	}

	protected void mostrarForm(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {

		request.getRequestDispatcher(path).forward(request, response);

	}
	protected void formPalabra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			List<Palabra> palabras = daoPalabra.listar("");
			request.setAttribute("palabras", palabras);
			
			List<Categoria> categorias = daoCategoria.listar("");
			request.setAttribute("categorias", categorias);

		} catch (ExceptionData e) {
			System.out.println(" ene el error " + e.getMessage());
			request.setAttribute("mensaje", e.getMessage());
		}

		mostrarForm(request, response, "nueva-palabra.jsp");

	}

	protected void insertPalabra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mensaje = "";
		JSONObject json = new JSONObject();
		try {

			String nombre = request.getParameter("name");
			String descripcion = request.getParameter("descripcion");
			String categoria = request.getParameter("categoria");
			
			Palabra palabra = new Palabra();
			palabra.setNombre(nombre);
			palabra.setDescripcion(descripcion);
			palabra.getCategoria().setIdCategoria(categoria);

			boolean retorno = daoPalabra.insert(palabra);
			mensaje = retorno ? "Palabra registrada correctamente" : "No se inserto ninguna palabra";
			json.put("estado", retorno);
			json.put("sms", mensaje);
		} catch (ExceptionData e) {
			
			json.put("estado", false);
			json.put("sms", e.getMessage());
		}
		catch (Exception e) {
			
			json.put("estado", false);
			json.put("sms", "Ocurrio un error inesperado, intentelo mas tarde");
		}

		response.getWriter().print(json.toString());

	}

	protected void formUpdatePalabra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject();
		try {

			String id = request.getParameter("id");
			Palabra palabra = daoPalabra.buscar(id);
			boolean estado = palabra != null;
			if (estado)
				json.put("palabra", UtilidadesPalabra.jsonString(palabra));
			else
				json.put("sms", "Palabra  con codigo " + id + " no registrada");
			json.put("estado", estado);

			// System.out.println("JSON Update "+ json.toJSONString());

		} catch (ExceptionData e) {
			System.out.println(" ene el error " + e.getMessage());
			json.put("estado", false);
			json.put("sms", e.getMessage());

		} catch (Exception e) {
			System.out.println("Errro execption " + e.getMessage());
			json.put("estado", false);
			json.put("sms","Ocurrio un error inesperado, intentelo mas tarde");

		}
		response.getWriter().print(json.toJSONString());

	}

	protected void actionUpdatePalabra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject();
		try {
			String id = request.getParameter("id");
			String nombre = request.getParameter("name");
			String descripcion = request.getParameter("descripcion");
			String categoria = request.getParameter("categoria");

			Palabra palabra = new Palabra();
			palabra.setIdPalabra(id);
			palabra.setNombre(nombre);
			palabra.setDescripcion(descripcion);
			palabra.getCategoria().setIdCategoria(categoria);

			boolean estado = daoPalabra.update(palabra);
			json.put("estado", estado);
			json.put("sms", estado ? "Palabra Actualizada correctamente"
					: "No se actualizo ningun registro");

		} catch (ExceptionData e) {
			// TODO: handle exception
			json.put("estado", false);
			json.put("sms", e.getMessage());
		
		}catch (Exception e) {
			// TODO: handle exception
			json.put("estado", false);
			json.put("sms", "Surguio un error inesperado, intentelo mas tarde");
		
		}

		response.getWriter().print(json.toJSONString());
	}

	protected void actionDeletePalabra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		JSONObject json = new JSONObject();
		try {
			String id = request.getParameter("id");
			boolean estado = daoPalabra.delete(id);
			json.put("estado", estado);
			json.put("sms", estado ? "Palabra Eliminada correctamente":"No se elimino ningun registro");
			
		} catch (ExceptionData e) {
			// TODO Auto-generated catch block
			json.put("estado", false);
			json.put("sms", e.getMessage());
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			json.put("estado", false);
			json.put("sms", "Ocurrio un error inesperado, intentelo mas tarde");
			
		}
		
		
		response.getWriter().print(json.toJSONString());
	}

	protected void actionBuscarPalabra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		JSONObject json = new JSONObject();
		String id = request.getParameter("id");

		try {

			Palabra palabra = daoPalabra.buscar(id);
			// System.out.println("Categoria consultada " + categoria);
			boolean estado = palabra !=null;			
			json.put("estado", estado);
			json.put("sms", estado? "Tabla Actualizada correctamente": "Categoria con id "+ id +" No existe" );
			if (estado) json.put("palabra",  UtilidadesPalabra.jsonString(palabra));

		}

		catch (ExceptionData e) {
			// TODO: handle exception
			json.put("estado", false);
			json.put("sms", e.getMessage());
		}

		catch (Exception e) {
			// TODO: handle exception
		
			json.put("estado", false);
			json.put("sms", "Surgio un error inesperado, intente mas tarde");
		}
		response.getWriter().print(json.toJSONString());
	}

	protected void ultimaPalabraInsertada(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject();
		try {

			Palabra palabra = daoPalabra.lastInsert();
			json.put("estado", true);
			json.put("sms", "Tabla Actualizada correctamente");
		    json.put("palabra", UtilidadesPalabra.jsonString(palabra));
		}

		catch (ExceptionData e) {
			json.put("estado", false);
			json.put("sms", e.getMessage());
		}

		catch (Exception e) {
			// TODO: handle exception
			json.put("estado", false);
			json.put("sms", "Surgio un error inesperado");

		}
		response.getWriter().print(json.toJSONString());
	}

}
