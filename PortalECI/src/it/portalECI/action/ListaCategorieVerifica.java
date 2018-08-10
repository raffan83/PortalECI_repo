package it.portalECI.action;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;

import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class listaCampioni
 */
@WebServlet(name="listaCategorieVerifica" , urlPatterns = { "/listaCategorieVerifica.do" })

public class ListaCategorieVerifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaCategorieVerifica() {
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
		
		try {
			
 			ArrayList<CategoriaVerificaDTO> listaCategorieVerifica =  (ArrayList<CategoriaVerificaDTO>) GestioneAccessoDAO.getListCategorieVerifica();
 
	        request.getSession().setAttribute("listaCategorieVerifica",listaCategorieVerifica);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaCategorieVerifica.jsp");
	     	dispatcher.forward(request,response);
		} catch (Exception ex) {
			
		//	ex.printStackTrace();
		     request.setAttribute("error",ECIException.callException(ex));
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/error.jsp");
		     dispatcher.forward(request,response);
		}
	}
}