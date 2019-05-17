package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneAttrezzatureBO;


/**
 * Servlet implementation class scadenzarioCreateAttrezzatura
 */
@WebServlet("/scadenzarioCreateAttrezzatura.do")
public class ScadenzarioCreateAttrezzatura extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScadenzarioCreateAttrezzatura() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(Utility.validateSession(request,response,getServletContext()))return;
		
		
		try
		{
			Session session = SessionFacotryDAO.get().openSession();
			session.beginTransaction();
		ArrayList<HashMap<String,Integer>> listaAttrezzature = GestioneAttrezzatureBO.getListaAttrezzatureScadenziario(session);

		
		ArrayList<String> lista_funzionamento = new ArrayList<>();
		ArrayList<String> lista_integrita = new ArrayList<>();
		ArrayList<String> lista_interna = new ArrayList<>();
		
	
			 Iterator it_funzionamento = listaAttrezzature.get(0).entrySet().iterator();
	
			    while (it_funzionamento.hasNext()) {
			        Map.Entry pair = (Map.Entry)it_funzionamento.next();
			        lista_funzionamento.add(pair.getKey() + ";" + pair.getValue());
			        it_funzionamento.remove(); 
			    }
			   
			    Iterator it_integrita = listaAttrezzature.get(1).entrySet().iterator();
				
			    while (it_integrita.hasNext()) {
			        Map.Entry pair = (Map.Entry)it_integrita.next();
			        lista_integrita.add(pair.getKey() + ";" + pair.getValue());
			        it_integrita.remove(); 
			    }
			    Iterator it_interna = listaAttrezzature.get(2).entrySet().iterator();
				
			    while (it_interna.hasNext()) {
			        Map.Entry pair = (Map.Entry)it_interna.next();
			        lista_interna.add(pair.getKey() + ";" + pair.getValue());
			        it_interna.remove(); 
			    }
		
		PrintWriter out = response.getWriter();
		
		 Gson gson = new Gson(); 
	        JsonObject myObj = new JsonObject();

	        JsonElement obj_funzionamento = gson.toJsonTree(lista_funzionamento);
	        JsonElement obj_integrita = gson.toJsonTree(lista_integrita);
	        JsonElement obj_interna = gson.toJsonTree(lista_interna);
	       
	       
	            myObj.addProperty("success", true);
	  
	        myObj.add("obj_funzionamento", obj_funzionamento);
	        myObj.add("obj_integrita", obj_integrita); 
	        myObj.add("obj_interna", obj_interna); 
	        
	        out.println(myObj.toString());

	        out.close();
		}
		catch(Exception ex)
    	{
			ex.printStackTrace();
	   		request.setAttribute("error",ECIException.callException(ex));
	   	    request.getSession().setAttribute("exception", ex);
	   		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/error.jsp");
	   	    dispatcher.forward(request,response);	
    	}  
	
	}

}
