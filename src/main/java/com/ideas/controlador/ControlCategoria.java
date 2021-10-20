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
import com.ideas.entidades.Categoria;
import com.ideas.utilidades.UtilidadesCategoria;

// @WebServlet(description = "Manejador de todas las peticiones", urlPatterns = { "/CMain" })
public class ControlCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOCategoria daoCategoria;

	public ControlCategoria() {

		super();
		daoCategoria = new DAOCategoria();

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

		// System.out.println("Action:" + request.getParameter("action"));
		String action = request.getParameter("action").toLowerCase();
		switch (action) {
			
		case "formcategoria":
			formCategoria(request, response);

			break;

		case "insertcategoria":
			insertCategoria(request, response);

			break;
		case "formupdatecategoria":
			formUpdateCategoria(request, response);

			break;
		case "actionupdatecategoria":
			actionUpdateCategoria(request, response);

			break;
		case "actiondeletecategoria":
			actionDeleteCategoria(request, response);

			break;
		case "actionbuscarcategoria":
			actionBuscarCategoria(request, response);

			break;
		case "ultimacategoria":

			ultimaCategoriaInsertada(request, response);

			break;

		default:
			break;
		}

	}

	protected void mostrarForm(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {

		request.getRequestDispatcher(path).forward(request, response);

	}
	protected void formCategoria(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			List<Categoria> categorias = daoCategoria.listar("");
			request.setAttribute("categorias", categorias);

		} catch (ExceptionData e) {
			System.out.println(" ene el error " + e.getMessage());
			request.setAttribute("mensaje", e.getMessage());
		}

		mostrarForm(request, response, "nueva-categoria.jsp");

	}

	protected void insertCategoria(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mensaje = "";
		JSONObject json = new JSONObject();
		try {

			String nombre = request.getParameter("name");
			String descripcion = request.getParameter("descripcion");

			Categoria categoria = new Categoria();
			categoria.setNombre(nombre);
			categoria.setDescripcion(descripcion);

			boolean retorno = daoCategoria.insert(categoria);
			mensaje = retorno ? "Categoria registrada correctamente" : "Surguio un problema al registrar la categoria";

			json.put("estado", retorno);
			json.put("sms", mensaje);
		} catch (Exception e) {

			mensaje = "Surgio un error inesperado, intentelo mas tarde";
			json.put("estado", false);
			json.put("sms", mensaje);
		}

		response.getWriter().print(json.toString());

	}

	protected void formUpdateCategoria(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject();
		try {

			String id = request.getParameter("id");
			Categoria categoria = daoCategoria.buscar(id);
			boolean estado = categoria != null;
			if (estado)
				json.put("categoria", UtilidadesCategoria.jsonString(categoria));
			else
				json.put("sms", "Categoria  con codigo " + id + " no registrada");
			json.put("estado", estado);

		} catch (ExceptionData e) {
			System.out.println(" ene el error " + e.getMessage());
			json.put("estado", false);
			json.put("sms", e.getMessage());

		}

		response.getWriter().print(json.toJSONString());

	}

	protected void actionUpdateCategoria(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject();
		try {
			String id = request.getParameter("id");
			String nombre = request.getParameter("name");
			String descripcion = request.getParameter("descripcion");

			Categoria categoria = new Categoria();
			categoria.setIdCategoria(id);
			categoria.setNombre(nombre);
			categoria.setDescripcion(descripcion);

			boolean estado = daoCategoria.update(categoria);
			json.put("estado", estado);
			json.put("sms", estado ? "Categoria Actualizada correctamente"
					: "Surguio un error inesperado, intentelo mas tarde");

		} catch (Exception e) {
			// TODO: handle exception
			json.put("estado", false);
			json.put("sms", "Surguio un error inesperado, intentelo mas tarde");
		
		}

		response.getWriter().print(json.toJSONString());
	}

	protected void actionDeleteCategoria(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		JSONObject json = new JSONObject();
		String id = request.getParameter("id");
		
		boolean estado =  daoCategoria.delete(id);
		
		json.put("estado", estado);
		json.put("sms", estado ? "Categoria Eliminada correctamente":"Surguio un error, intentelo mas tarde");
		
		response.getWriter().print(json.toJSONString());
	}

	protected void actionBuscarCategoria(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		JSONObject json = new JSONObject();
		String id = request.getParameter("id");

		try {

			Categoria categoria = daoCategoria.buscar(id);
			// System.out.println("Categoria consultada " + categoria);
			boolean estado = categoria !=null;			
			json.put("estado", estado);
			json.put("sms", estado? "Tabla Actualizada correctamente": "Categoria con id "+ id +" No existe" );
			if (estado) json.put("categoria",  UtilidadesCategoria.jsonString(categoria));

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

	protected void ultimaCategoriaInsertada(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject();
		try {

			Categoria categoria = daoCategoria.lastInsert();
			json.put("estado", true);
			json.put("sms", "Tabla Actualizada correctamente");
			json.put("categoria", UtilidadesCategoria.jsonString(categoria));
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
