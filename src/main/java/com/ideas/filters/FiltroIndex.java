package com.ideas.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FiltroIndex implements Filter {
    public FiltroIndex() {
        // TODO Auto-generated constructor stub
    }

	
	public void destroy() {
	
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		System.out.println(this.getClass().getSimpleName());
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession sesion = req.getSession();
		
		if (sesion.getAttribute("usuario") !=null) {
			req.getRequestDispatcher("menu-principal.jsp").forward(req, res);
			
		}else chain.doFilter(request, response);
		
		
	}


	

}
