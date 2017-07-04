package it.portalECI.action;

import it.portalECI.DAO.GestioneAccessoDAO;

import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.RuoloDTO;

import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class listaCampioni
 */
@WebServlet(name="listaCompany" , urlPatterns = { "/listaCompany.do" })

public class ListaCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaCompany() {
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

		
		response.setContentType("text/html");
		
		try 
		{
			
 			ArrayList<CompanyDTO> listaCompany =  (ArrayList<CompanyDTO>) GestioneAccessoDAO.getListCompany();
 

	        request.getSession().setAttribute("listaCompany",listaCompany);


			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaCompany.jsp");
	     	dispatcher.forward(request,response);
		} 
		catch (Exception ex) {
			
		//	ex.printStackTrace();
		     request.setAttribute("error",ECIException.callException(ex));
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/error.jsp");
		     dispatcher.forward(request,response);
		}
	
	}

}
