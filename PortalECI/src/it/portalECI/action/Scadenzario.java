package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import it.portalECI.Util.Costanti;
import it.portalECI.Util.Utility;
import it.portalECI.DAO.GestioneCampioneDAO;
import it.portalECI.DTO.AcAttivitaCampioneDTO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.CompanyDTO;

import it.portalECI.Exception.ECIException;
import it.portalECI.bo.GestioneAttivitaCampioneBO;
import it.portalECI.bo.GestioneCampioneBO;

import it.portalECI.DTO.TipoCampioneDTO;

import it.portalECI.bo.GestioneCompanyBO;

import it.portalECI.bo.CreateSchedaScadenzarioCampioni;
import it.portalECI.DAO.SessionFacotryDAO;

/**
 * Servlet implementation class Scadenzario
 */
@WebServlet("/scadenzario.do")
public class Scadenzario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Scadenzario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		boolean ajax = false;
		String action = request.getParameter("action");
		
		
		JsonObject myObj = new JsonObject();
		CompanyDTO cmp=(CompanyDTO)request.getSession().getAttribute("usrCompany");
		
		try {	
		if(action == null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/scadenzario.jsp");
		    dispatcher.forward(request,response);
		    
		    session.close();
		}
		else if(action!= null && action.equals("create_scadenzario")) {

				String id_campione = request.getParameter("id_campione");


				
					ArrayList<HashMap<String,Integer>> listaScadenze = null;
				if(id_campione!=null) {
					CampioneDTO	campione = GestioneCampioneDAO.getCampioneFromId(id_campione);				
					
					listaScadenze = GestioneAttivitaCampioneBO.getListaAttivitaScadenziarioCampione(campione, session);
				}else {
					listaScadenze = GestioneAttivitaCampioneBO.getListaAttivitaScadenziario(session);
				}
				
				
				ArrayList<String> lista_tarature = new ArrayList<>();
				ArrayList<String> lista_verifiche_intermedie= new ArrayList<>();
				ArrayList<String> lista_manutenzioni = new ArrayList<>();
				
			
					 Iterator id_manutenzione = listaScadenze.get(0).entrySet().iterator();
			
					    while (id_manutenzione.hasNext()) {
					        Map.Entry pair = (Map.Entry)id_manutenzione.next();
					        lista_manutenzioni.add(pair.getKey() + ";" + pair.getValue());
					        id_manutenzione.remove(); 
					    }
					   
					    Iterator it_verifica = listaScadenze.get(1).entrySet().iterator();
						
					    while (it_verifica.hasNext()) {
					        Map.Entry pair = (Map.Entry)it_verifica.next();
					        lista_verifiche_intermedie.add(pair.getKey() + ";" + pair.getValue());
					        it_verifica.remove(); 
					    }
					    Iterator it_taratura = listaScadenze.get(2).entrySet().iterator();
						
					    while (it_taratura.hasNext()) {
					        Map.Entry pair = (Map.Entry)it_taratura.next();
					        lista_tarature.add(pair.getKey() + ";" + pair.getValue());
					        it_taratura.remove(); 
					    }
				

				
				 Gson gson = new Gson(); 
			        
//			        JsonElement obj_funzionamento = gson.toJsonTree(lista_funzionamento);
//			        JsonElement obj_integrita = gson.toJsonTree(lista_integrita);
//			        JsonElement obj_interna = gson.toJsonTree(lista_interna);
			        
			        JsonElement obj_manutenzione = gson.toJsonTree(lista_manutenzioni);
			        JsonElement obj_verifica = gson.toJsonTree(lista_verifiche_intermedie);
			        JsonElement obj_taratura = gson.toJsonTree(lista_tarature);
			       		       
			        myObj.addProperty("success", true);
			  
			        myObj.add("obj_manutenzione", obj_manutenzione);
			        myObj.add("obj_verifica", obj_verifica); 
			        myObj.add("obj_taratura", obj_taratura); 
			        
			        PrintWriter out = response.getWriter();
			        
			        out.println(myObj.toString());

			        out.close();
			        
			        session.getTransaction().commit();
		        	session.close();
				
			        
			}
			 
		else if(action.equals("campioni_scadenza")) {
			
			String date =request.getParameter("date");
			String tipo_data_lat =request.getParameter("tipo_data_lat");
			String manutenzione =request.getParameter("manutenzione");

			ArrayList<CampioneDTO> listaCampioni=new ArrayList<CampioneDTO>();
			
		   	 CompanyDTO company=(CompanyDTO)request.getSession().getAttribute("usrCompany");

			ArrayList<CompanyDTO> lista_company = new ArrayList<CompanyDTO>();
			lista_company.add(company);
		
			if(tipo_data_lat!=null) {
					if(date.length()>=10)
					{
//						boolean manutenzione = false;
//						if(tipo_data_lat.equals("1")) {
//							manutenzione = true;
//						}
						listaCampioni =GestioneAttivitaCampioneBO.getListaCampioniPerData(date.substring(0,10), tipo_data_lat);
					}
				}else if(manutenzione!= null) {
					if(date.length()>=10)
					{						
						listaCampioni =GestioneAttivitaCampioneBO.getListaCampioniPerData(date.substring(0,10), null);
					}
				}

			
		        request.getSession().setAttribute("lista_company", lista_company);


			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			ArrayList<TipoCampioneDTO> listaTipoCampione= GestioneCampioneDAO.getListaTipoCampione();
			request.getSession().setAttribute("listaTipoCampione",listaTipoCampione);
			request.getSession().setAttribute("listaCampioni",listaCampioni);
			
		
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaCampioni.jsp");
		     dispatcher.forward(request,response);
			
	
		}
		
		else if(action!=null && action.equals("esporta_campioni_scadenza")) {
			
			ajax = true;
			
			String data_start = request.getParameter("data_start");
			String data_end = request.getParameter("data_end");

						
			JsonArray listaCampioni = GestioneCampioneBO.getCampioniScadenzaDate(data_start, data_end, cmp.getId());
		
			JsonArray campioni = (JsonArray) listaCampioni.get(0);
			JsonArray descrizioni = (JsonArray)listaCampioni.get(1);
			JsonArray date = (JsonArray)listaCampioni.get(2);
			
			PrintWriter out = response.getWriter();
			if(campioni.size()>0) {
				Type listType = new TypeToken<ArrayList<CampioneDTO>>(){}.getType();
				ArrayList<CampioneDTO> listaCamp = new Gson().fromJson(campioni, listType);
				
				listType = new TypeToken<ArrayList<Integer>>(){}.getType();
				ArrayList<Integer> listaDesc = new Gson().fromJson(descrizioni, listType);
				
				listType = new TypeToken<ArrayList<String>>(){}.getType();
				ArrayList<String> listaDate = new Gson().fromJson(date, listType);				
				
				new CreateSchedaScadenzarioCampioni(listaCamp, listaDesc, listaDate, data_start, data_end);
				
				
				
				myObj.addProperty("success", true);
				out.print(myObj);
				
			}else {
				
				myObj.addProperty("success", false);
				myObj.addProperty("messaggio", "Nessun campione in scadenza nel periodo selezionato!");
				out.print(myObj);
			}
		}
		else if(action.equals("download_scadenzario")) {
			
			File file = new File(Costanti.PATH_ROOT+"//ScadenzarioCampioni//SchedaListacampioni.pdf");
			
			FileInputStream fileIn = new FileInputStream(file);
			
			response.setContentType("application/octet-stream");				
			  
			 response.setHeader("Content-Disposition","attachment;filename="+ file.getName());
			 
			 ServletOutputStream outp = response.getOutputStream();
			     
			    byte[] outputByte = new byte[1];
			    
			    while(fileIn.read(outputByte, 0, 1) != -1)
			    {
			    	outp.write(outputByte, 0, 1);
			    }
			    
			    fileIn.close();
			    outp.flush();
			    outp.close();
			
		}
		
		
		}
		catch(Exception ex)
    	{
			PrintWriter out = response.getWriter();
			if(ajax) {
				ex.printStackTrace();
	        	session.getTransaction().rollback();
	        	session.close();
	        	request.getSession().setAttribute("exception", ex);
	        	//myObj.addProperty("success", false);
	        	//myObj.addProperty("messaggio", STIException.callException(ex).toString());
	        	myObj = ECIException.callExceptionJsonObject(ex);
	        	out.println(myObj.toString());
	        	  	
			}else {
	    		 ex.printStackTrace();
	    	     request.setAttribute("error",ECIException.callException(ex));
	       	     request.getSession().setAttribute("exception", ex);
	    		 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/error.jsp");
	    	     dispatcher.forward(request,response);
			}
    	} 
	}
}
