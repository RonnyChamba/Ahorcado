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

	private FilterConfig filterConfig ;
 
    public FiltroMain() {
        // TODO Auto-generated constructor stub
    }

	
    public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
    	this.filterConfig =fConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	 
		System.out.println(this.getClass().getSimpleName());
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession sesion = req.getSession();
		// String paraterExclusion = filterConfig.getInitParameter("fileExclusion");	
		
			if (sesion.getAttribute("usuario") !=null) {
				
				Jugador juaJugador = (Jugador) sesion.getAttribute("usuario");
				
				// Si no es ADMIN valido a que recurso va a acceder
				if (!juaJugador.getTipo().equalsIgnoreCase("ADMIN")) {
					
					// Recursos a la que el acceso es solo para los ADMIN
					if ("/nueva-categoria.jsp".startsWith(req.getServletPath()) 
							|| "/nueva-palabra.jsp".startsWith(req.getServletPath())
							|| "/nuevo-jugador.jsp".startsWith(req.getServletPath())) {
						
						res.sendRedirect("menu-principal.jsp");
					
					// Si no es Admin pero desea acceder a un recurso permitido, lo dejo pasar
					}else chain.doFilter(req, res);
					
				// Si es ADMIN, Lo dejo pasar
				}else chain.doFilter(req, res);
			}else  res.sendRedirect("index.jsp");			
			
			
	
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	

}
