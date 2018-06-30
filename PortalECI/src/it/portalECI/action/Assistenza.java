package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.gson.JsonParser;

import com.lowagie.text.pdf.codec.Base64.InputStream;


import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CompanyDTO;

import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;


/**
 * Servlet implementation class listaCampioni
 */
@WebServlet(name="assistenza" , urlPatterns = { "/assistenza.do" })

public class Assistenza extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Assistenza() {
    	
    	
    	
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;
					
		try {

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/assistenza.jsp");
	     	dispatcher.forward(request,response);
			
		}catch(Exception ex){
   		 	ex.printStackTrace();
   	     	request.setAttribute("error",ECIException.callException(ex));
   		 	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/error.jsp");
   	     	dispatcher.forward(request,response);	
    	} 	
	}
}
