package com.ideas.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.portable.ValueBase;

import com.ideas.entidades.Jugador;

public class FiltroMain implements Filter {

	private FilterConfig filterConfig;

	public FiltroMain() {
		// TODO Auto-generated constructor stub
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = fConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession sesion = req.getSession();

		if (sesion.getAttribute("usuario") != null)
			sesionActive(req, res, sesion, chain);
		else
			res.sendRedirect("index.jsp");

	}

	public void sesionActive(HttpServletRequest request, HttpServletResponse response, HttpSession sesion,
			FilterChain chain) throws IOException, ServletException {

		Jugador jugador = (Jugador) sesion.getAttribute("usuario");

		// Validar tipo de usuario
		if (!jugador.getTipo().equalsIgnoreCase("ADMIN"))
			sesionActiveNormal(request, response, sesion, chain);

		// ADMIN
		else
			sesionActiveAdmin(request, response, sesion, chain);

	}

	public void sesionActiveAdmin(HttpServletRequest request, HttpServletResponse response, HttpSession sesion,
			FilterChain chain) throws IOException, ServletException {

		String action = "";
		String servlet = "";

		if ("/nueva-categoria.jsp".startsWith(request.getServletPath())) {
			action = "formCategoria";
			servlet = "CCategoria";
		}
		if ("/nueva-palabra.jsp".startsWith(request.getServletPath())) {
			action = "formPalabra";
			servlet = "CPalabra";
		}

		if ("/nuevo-jugador.jsp".startsWith(request.getServletPath())) {
			action = "formJugador";
			servlet = "CJugador";
		}
		if ("/mis-juegos.jsp".startsWith(request.getServletPath())) {
			action = "formMisJuegos";
			servlet = "CJugador";
		}

		if (!action.equalsIgnoreCase("") && !servlet.equalsIgnoreCase(""))
			request.getRequestDispatcher(servlet + "?action=" + action).forward(request, response);
		else {
			// Si los datos de la persona de piensan en actualizar aqui tabmbien tendria
			// que dirigirme al Servlet para consultarlos de nuevo, para que esten
			// actualizados

			System.out.println("RUTA " + request.getServletPath());
			chain.doFilter(request, response);
		}

	}

	public void sesionActiveNormal(HttpServletRequest request, HttpServletResponse response, HttpSession sesion,
			FilterChain chain) throws IOException, ServletException {

		// Recursos denegados, solo valido para los ADMIN
		if ("/nueva-categoria.jsp".startsWith(request.getServletPath())
				|| "/nueva-palabra.jsp".startsWith(request.getServletPath())
				|| "/nuevo-jugador.jsp".startsWith(request.getServletPath())) {

			response.sendRedirect("menu-principal.jsp");
			return;
		}
		
		// A  acceder a este jsp siempre debe abrir desde el servelt,tanto admin como jugador
		if ("/mis-juegos.jsp".startsWith(request.getServletPath())) {
			request.getRequestDispatcher("CJugador" + "?action=" + "formMisJuegos").forward(request, response);
		}else chain.doFilter(request, response);

	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

}
