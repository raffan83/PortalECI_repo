package it.portalECI.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneCampioneDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CampioneDTO;

import it.portalECI.DTO.TipoCampioneDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneUtenteBO;


/**
 * Servlet implementation class DettaglioCampione
 */
@WebServlet(name="dettaglioCampione" , urlPatterns = { "/dettaglioCampione.do" })

public class DettaglioCampione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DettaglioCampione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
	try{	
		String idC = request.getParameter("idCamp");


		CampioneDTO dettaglio =GestioneCampioneDAO.getCampioneFromId(idC);	
		
		ArrayList<TipoCampioneDTO> listaTipoCampione= GestioneCampioneDAO.getListaTipoCampione();
		
		ArrayList<UtenteDTO> listaVerificatori = (ArrayList<UtenteDTO>) GestioneUtenteBO.getTecnici("2", session);

		 Gson gson = new Gson(); 
	        JsonObject myObj = new JsonObject();

	        JsonElement obj = gson.toJsonTree(dettaglio);	       

	            myObj.addProperty("success", true);

	       
	        myObj.add("dataInfo", obj);
	        myObj.add("verificatori_json", gson.toJsonTree(listaVerificatori));
	        request.getSession().setAttribute("myObj",myObj);
	        request.getSession().setAttribute("listaTipoCampione",listaTipoCampione);

	        session.close();
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/dettaglioCampione.jsp");
		     dispatcher.forward(request,response);

	
	}catch(Exception ex)
	{
		session.close();
		 ex.printStackTrace();
	     request.setAttribute("error",ECIException.callException(ex));
	     request.getSession().setAttribute("exception",ex);
		 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/error.jsp");
	     dispatcher.forward(request,response);
		
	}  
	
	}
	
	static CampioneDTO getCampione(ArrayList<CampioneDTO> listaCampioni,String idC) {
		CampioneDTO campione =null;
		
		try
		{		
		for (int i = 0; i < listaCampioni.size(); i++) {
			
			if(listaCampioni.get(i).getId()==Integer.parseInt(idC))
			{
				return listaCampioni.get(i);
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			campione=null;
			throw ex;
		}
		return campione;
	}

}
