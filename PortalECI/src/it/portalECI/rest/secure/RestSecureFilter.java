package it.portalECI.rest.secure;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.portalECI.DTO.UtenteDTO;

/**
 * Servlet Filter implementation class RestSecureFilter
 */
@WebFilter(
		servletNames = { 
				"InterventoREST", 
				"CommessaREST", 
				"VerbaleREST"
		})
public class RestSecureFilter implements Filter {

    /**
     * Default constructor. 
     */
    public RestSecureFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		 HttpServletRequest httpRequest = (HttpServletRequest) request;
	     Enumeration<String> headerNames = httpRequest.getHeaderNames();
	     String authHeader=null;
	        if (headerNames != null) {
	        	authHeader=httpRequest.getHeader("X-ECI-Auth");
	        }
	     
	     if(authHeader!=null) {
	    	 //
	    	 UtenteDTO utente=JWTUtility.validateToken(authHeader);
	    	 if(utente!=null) {
	    		 request.setAttribute("x-user",utente);
	    		 chain.doFilter(request, response);
	    		 return;
	    	 }
	    	 
	     }
	    	 HttpServletResponse httpResponse = (HttpServletResponse) response;
	    	 httpResponse.setStatus(httpResponse.SC_UNAUTHORIZED);
	    
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
