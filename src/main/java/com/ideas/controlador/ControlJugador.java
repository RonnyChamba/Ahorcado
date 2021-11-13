package com.ideas.controlador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.ideas.commons.ExceptionData;
import com.ideas.dao.DAOJugador;
import com.ideas.entidades.Jugador;

// @WebServlet(description = "Para manejar  las vistas de los jugadores", urlPatterns = { "/CJugador" })
public class ControlJugador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOJugador daoJugador;

	public ControlJugador() {
		super();

		daoJugador = new DAOJugador();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// System.out.println("Action:" + request.getParameter("action"));
		String action = request.getParameter("action").toLowerCase();

		switch (action) {

		case "login":
			login(request, response);
			break;

		case "formjugador":
			request.setAttribute("admin", "admin");
			mostrarForm(request, response, "nuevo-jugador.jsp");
			break;

		case "nuevojugador":
			insertJugador(request, response);
			break;

		default:
			break;
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			String usuario = request.getParameter("user");
			String password = request.getParameter("clave");

			Jugador jugadorLogin = daoJugador.login(usuario, password);

			if (jugadorLogin != null) {

				HttpSession miSesion = request.getSession(true);
				
				ControlJuego ct = new ControlJuego();
				ct.actualizarJuegosJugador(jugadorLogin, miSesion, "3");
				// miSesion.setAttribute("usuario", jugadorLogin);
				request.getRequestDispatcher("menu-principal.jsp").forward(request, response);

			} else {
				request.setAttribute("mensaje", "Usuario no encontrado");
				request.getRequestDispatcher("index.jsp").forward(request, response);

			}

		} catch (ExceptionData e) {

			request.setAttribute("mensaje", e.getMessage());
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	private void insertJugador(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject();
		try {

			String dni = request.getParameter("dni");

			if (!daoJugador.isExiste(dni)) {
				String tipo = request.getParameter("tipoJugador");
				String nombre = request.getParameter("name");
				String ciudad = request.getParameter("ciudad");
				String clave = request.getParameter("clave");
				String puntaje = request.getParameter("puntaje");

				Jugador jugador = new Jugador();
				
				jugador.setTipo(tipo == null ? "JUGADOR" : tipo);
				jugador.setCedula(dni);
				jugador.setNombre(nombre);
				jugador.setCiudad(ciudad);
				jugador.setClave(clave);
				jugador.setPuntaje(Double.parseDouble(puntaje.equalsIgnoreCase("") ? "0" : puntaje));

				boolean estado = daoJugador.insert(jugador);
				json.put("estado", estado);
				json.put("sms", estado ? "Jugador registrado correctamente" : "Ocurrio un error, intentelo mas tarde");

			} else {

				json.put("estado", false);
				json.put("sms", "La cedula " + dni + " ya se encuentra registrada,ingrese una diferente");
			}

		} catch (ExceptionData e) {

			json.put("estado", false);
			json.put("sms", e.getMessage());

		}

		catch (Exception e) {

			json.put("estado", false);
			json.put("sms", "Ocurrio un error inesperado, intentelo mas tarde");
		}
		response.getWriter().print(json.toJSONString());

	}

	private void mostrarForm(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {

		request.getRequestDispatcher(path).forward(request, response);

	}

}
