package it.portalECI.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneInterventoBO;

/**
 * Servlet implementation class GestioneIntervento
 */
@WebServlet(name = "gestioneInterventoDati", urlPatterns = { "/gestioneInterventoDati.do" })
public class GestioneInterventoDati extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneInterventoDati() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		String idIntervento=request.getParameter("idIntervento");
		
		InterventoDTO intervento= GestioneInterventoBO.getIntervento(idIntervento, session);
		
		System.out.println(intervento.getIdCommessa());

		request.getSession().setAttribute("intervento", intervento);
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneInterventoDati.jsp");
		dispatcher.forward(request,response);
		
		session.getTransaction().commit();
		session.close();	
	}
}