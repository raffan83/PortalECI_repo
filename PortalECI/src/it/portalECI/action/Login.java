package it.portalECI.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.bo.GestioneGraficiBO;
import it.portalECI.bo.GestioneVersionePortaleBO;

/**
 * Servlet implementation class Login
 */
@WebServlet(name="login" , urlPatterns = { "/login.do" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		UtenteDTO utente = (UtenteDTO) request.getSession().getAttribute("userObj");
		
		if(utente!=null)
        {
			Session session = SessionFacotryDAO.get().openSession();
			session.beginTransaction();
			request.setAttribute("forward","site/home.jsp"); 	
			request.getSession().setAttribute("nomeUtente","  "+utente.getNominativo());
			request.getSession().setAttribute("idUtente",utente.getId());
			request.getSession().setAttribute("tipoAccount",utente.getTipoutente());
			
			request.getSession().setAttribute("userObj", utente);
			request.getSession().setAttribute("usrCompany", utente.getCompany());
			
			ArrayList<ArrayList<String>> lista_dati = GestioneGraficiBO.getGraficoTipoVerifica(utente);
			HashMap<String, Integer> map_verbali = GestioneGraficiBO.getGraficoVerbaliVerificatore(utente);
			ArrayList<HashMap<String, String>>  list_map = GestioneGraficiBO.getGraficoStatiVerbali(utente);
			String versione_portale = GestioneVersionePortaleBO.getVersioneCorrente(session);
			
			HashMap<String, String> map_stati = list_map.get(0);
			HashMap<String, String> map_color = list_map.get(1);
			
			Gson gson = new Gson();
			
			JsonElement obj_codice_verifica = gson.toJsonTree(lista_dati.get(0));
			JsonElement obj_codice_categoria = gson.toJsonTree(lista_dati.get(1));				
			
			request.getSession().setAttribute("obj_codice_verifica", obj_codice_verifica);
			request.getSession().setAttribute("obj_codice_categoria", obj_codice_categoria);
			request.getSession().setAttribute("map_stati",gson.toJsonTree(map_stati).toString());
			request.getSession().setAttribute("map_verbali",gson.toJsonTree(map_verbali).toString());
			request.getSession().setAttribute("map_color",gson.toJsonTree(map_color).toString());
			request.getSession().setAttribute("versione_portale",versione_portale);
			
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/dashboard.jsp");
			dispatcher.forward(request,response);
			
			session.close();
        }else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
			dispatcher.forward(request,response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//if(Utility.validateSession(request,response,getServletContext()))return;
			
		try{
			response.setContentType("text/html");
		        
			String user=request.getParameter("uid");
			String pwd=request.getParameter("pwd");
		        
			UtenteDTO utente=GestioneAccessoDAO.controllaAccesso(user,pwd);
		        
			if(utente!=null){
				Session session = SessionFacotryDAO.get().openSession();
				session.beginTransaction();
				request.setAttribute("forward","site/home.jsp"); 	
				request.getSession().setAttribute("nomeUtente","  "+utente.getNominativo());
				request.getSession().setAttribute("idUtente",utente.getId());
				request.getSession().setAttribute("tipoAccount",utente.getTipoutente());
				
				request.getSession().setAttribute("userObj", utente);
				request.getSession().setAttribute("usrCompany", utente.getCompany());
				String versione_portale = GestioneVersionePortaleBO.getVersioneCorrente(session);
				
				ArrayList<ArrayList<String>> lista_dati = GestioneGraficiBO.getGraficoTipoVerifica(utente);
				HashMap<String, Integer> map_verbali = GestioneGraficiBO.getGraficoVerbaliVerificatore(utente);
				//HashMap<String, Integer>  map_stati = GestioneGraficiBO.getGraficoStatiVerbali(utente);
				ArrayList<HashMap<String, String>>  list_map = GestioneGraficiBO.getGraficoStatiVerbali(utente);
				
				Gson gson = new Gson();
				
				if(utente.checkRuolo("CLVAL")) {
					HashMap<String, Integer>  lista_dati_cliente_val = GestioneGraficiBO.getGraficoClienteVAL(utente);	
					request.getSession().setAttribute("lista_dati_cliente_val", gson.toJsonTree(lista_dati_cliente_val).toString());
				}
				if(utente.checkRuolo("CLVIE")) {
					HashMap<String, Integer>  lista_dati_cliente_vie = GestioneGraficiBO.getGraficoClienteVIE(utente);	
					request.getSession().setAttribute("lista_dati_cliente_vie", gson.toJsonTree(lista_dati_cliente_vie).toString());
				}
				
				
				
				HashMap<String, String> map_stati = list_map.get(0);
				HashMap<String, String> map_color = list_map.get(1);
				
			
				
				JsonElement obj_codice_verifica = gson.toJsonTree(lista_dati.get(0));
				JsonElement obj_codice_categoria = gson.toJsonTree(lista_dati.get(1));				
				
				request.getSession().setAttribute("obj_codice_verifica", obj_codice_verifica);
				request.getSession().setAttribute("obj_codice_categoria", obj_codice_categoria);
				request.getSession().setAttribute("map_stati",gson.toJsonTree(map_stati).toString());
				request.getSession().setAttribute("map_verbali",gson.toJsonTree(map_verbali).toString());
				request.getSession().setAttribute("map_color",gson.toJsonTree(map_color).toString());
				request.getSession().setAttribute("versione_portale",versione_portale);
				
				session.close();
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/dashboard.jsp");
				dispatcher.forward(request,response);
			}else{
				request.setAttribute("errorMessage", "Username o Password non validi");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request,response);
		             
			}
		        
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("error",ECIException.callException(ex));
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/error.jsp");
			dispatcher.forward(request,response);	
		}  
	}
}
