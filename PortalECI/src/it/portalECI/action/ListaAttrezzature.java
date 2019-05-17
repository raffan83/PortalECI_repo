package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneAttrezzatureBO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.SedeDTO;
import it.portalECI.bo.GestioneAnagraficaRemotaBO;



/**
 * Servlet implementation class ListaAttrezzature
 */
@WebServlet(name="listaAttrezzature" , urlPatterns = { "/listaAttrezzature.do" })

public class ListaAttrezzature extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaAttrezzature() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(Utility.validateSession(request,response,getServletContext()))return;
		
		response.setContentType("text/html");
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		JsonObject myObj = new JsonObject();
		boolean ajax = false;
		String action = request.getParameter("action");
		try {
			if(action==null || action.equals("")) {
				
				CompanyDTO cmp=(CompanyDTO)request.getSession().getAttribute("usrCompany");
				
				String idCompany=""+cmp.getId();
				
				List<ClienteDTO> listaClientiFull = GestioneAnagraficaRemotaBO.getListaClienti(idCompany);				

				request.getSession().setAttribute("listaClienti",listaClientiFull);
				
				List<SedeDTO> listaSediFull = GestioneAnagraficaRemotaBO.getListaSedi();

				request.getSession().setAttribute("listaSedi",listaSediFull);	
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaAttrezzature.jsp");
		     	dispatcher.forward(request,response);
			}
			else if(action.equals("cliente_sede")) {
				
				String id_cliente = request.getParameter("id_cliente");
				String id_sede = request.getParameter("id_sede");
				
				ArrayList<AttrezzaturaDTO> lista_attrezzature = GestioneAttrezzatureBO.getlistaAttrezzatureSede(Integer.parseInt(id_cliente), Integer.parseInt(id_sede.split("_")[0]),session);
				
				 request.getSession().setAttribute("lista_attrezzature",lista_attrezzature);
				
	
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaAttrezzatureSede.jsp");
		     	dispatcher.forward(request,response);
			}
			else if(action.equals("nuovo")) {
				
				ajax=true;
				
				String id_cliente = request.getParameter("id_cliente");
				String id_sede = request.getParameter("id_sede");
				String matricola_inail = request.getParameter("matricola_inail");
				String numero_fabbrica = request.getParameter("numero_fabbrica");
				String descrizione = request.getParameter("descrizione");
				String tipo_attivita = request.getParameter("tipo_attivita");
				String data_ver_funz = request.getParameter("data_ver_funz");
				String data_pross_ver_funz = request.getParameter("data_pross_ver_funz");
				String data_ver_integrita = request.getParameter("data_ver_integrita");
				String data_pross_ver_integrita = request.getParameter("data_pross_ver_integrita");
				String data_ver_interna = request.getParameter("data_ver_interna");
				String data_pross_ver_interna = request.getParameter("data_pross_ver_interna");
				
				List<SedeDTO> listaSedi = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");
				SedeDTO sede = null;
				if(!id_sede.equals("0")) {
					sede = GestioneAnagraficaRemotaBO.getSedeFromId(listaSedi, Integer.parseInt(id_sede.split("_")[0]), Integer.parseInt(id_cliente));
				}
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				
				AttrezzaturaDTO attrezzatura = new AttrezzaturaDTO();
				attrezzatura.setId_cliente(Integer.parseInt(id_cliente));				
				attrezzatura.setId_sede(Integer.parseInt(id_sede.split("_")[0]));							
				attrezzatura.setNome_cliente(GestioneAnagraficaRemotaBO.getClienteById(id_cliente).getNome());	
				if(!id_sede.equals("0")) {
					attrezzatura.setNome_sede(sede.getDescrizione() +" - "+sede.getIndirizzo()+" - "+sede.getComune()+ "("+sede.getSiglaProvincia()+")");
				}else {
					attrezzatura.setNome_sede("Non Associate");
				}				
				attrezzatura.setMatricola_inail(matricola_inail);
				attrezzatura.setNumero_fabbrica(numero_fabbrica);
				attrezzatura.setDescrizione(descrizione);
				attrezzatura.setTipo_attivita(tipo_attivita);
				if(data_ver_funz!=null && !data_ver_funz.equals("")) {
					attrezzatura.setData_verifica_funzionamento(format.parse(data_ver_funz));
				}
				if(data_pross_ver_funz!=null && !data_pross_ver_funz.equals("")) {
					attrezzatura.setData_prossima_verifica_funzionamento(format.parse(data_pross_ver_funz));	
				}
				if(data_ver_integrita!=null && !data_ver_integrita.equals("")) {
					attrezzatura.setData_verifica_integrita(format.parse(data_ver_integrita));	
				}
				if(data_pross_ver_integrita!=null && !data_pross_ver_integrita.equals("")) {
					attrezzatura.setData_prossima_verifica_integrita(format.parse(data_pross_ver_integrita));	
				}
				if(data_ver_interna!=null && !data_ver_interna.equals("")) {
					attrezzatura.setData_verifica_interna(format.parse(data_ver_interna));	
				}
				if(data_pross_ver_interna!=null && !data_pross_ver_interna.equals("")) {
					attrezzatura.setData_prossima_verifica_interna(format.parse(data_pross_ver_interna));	
				}				
				
				session.save(attrezzatura);
				session.getTransaction().commit();
				session.close();
				
				PrintWriter out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Attrezzatura salvata con successo!");
	        	out.print(myObj);
				
			}
			else if(action.equals("modifica")) {
				
				ajax=true;
				
				String id_attrezzatura = request.getParameter("id_attrezzatura");
				String id_cliente = request.getParameter("id_cliente");
				String id_sede = request.getParameter("id_sede");
				String matricola_inail = request.getParameter("matricola_inail");
				String numero_fabbrica = request.getParameter("numero_fabbrica");
				String descrizione = request.getParameter("descrizione");
				String tipo_attivita = request.getParameter("tipo_attivita");
				String data_ver_funz = request.getParameter("data_ver_funz");
				String data_pross_ver_funz = request.getParameter("data_pross_ver_funz");
				String data_ver_integrita = request.getParameter("data_ver_integrita");
				String data_pross_ver_integrita = request.getParameter("data_pross_ver_integrita");
				String data_ver_interna = request.getParameter("data_ver_interna");
				String data_pross_ver_interna = request.getParameter("data_pross_ver_interna");
				
				
				List<SedeDTO> listaSedi = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");
				SedeDTO sede = null;
				if(!id_sede.equals("0")) {
					sede = GestioneAnagraficaRemotaBO.getSedeFromId(listaSedi, Integer.parseInt(id_sede.split("_")[0]), Integer.parseInt(id_cliente));
				}
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				
				AttrezzaturaDTO attrezzatura = GestioneAttrezzatureBO.getAttrezzaturaFromId(Integer.parseInt(id_attrezzatura), session);
				attrezzatura.setId_cliente(Integer.parseInt(id_cliente));				
				attrezzatura.setId_sede(Integer.parseInt(id_sede.split("_")[0]));							
				attrezzatura.setNome_cliente(GestioneAnagraficaRemotaBO.getClienteById(id_cliente).getNome());	
				if(!id_sede.equals("0")) {
					attrezzatura.setNome_sede(sede.getDescrizione() +" - "+sede.getIndirizzo()+" - "+sede.getComune()+ "("+sede.getSiglaProvincia()+")");
				}else {
					attrezzatura.setNome_sede("Non Associate");
				}				
				attrezzatura.setMatricola_inail(matricola_inail);
				attrezzatura.setNumero_fabbrica(numero_fabbrica);
				attrezzatura.setDescrizione(descrizione);
				attrezzatura.setTipo_attivita(tipo_attivita);
				if(data_ver_funz!=null && !data_ver_funz.equals("")) {
					attrezzatura.setData_verifica_funzionamento(format.parse(data_ver_funz));
				}
				if(data_pross_ver_funz!=null && !data_pross_ver_funz.equals("")) {
					attrezzatura.setData_prossima_verifica_funzionamento(format.parse(data_pross_ver_funz));	
				}
				if(data_ver_integrita!=null && !data_ver_integrita.equals("")) {
					attrezzatura.setData_verifica_integrita(format.parse(data_ver_integrita));	
				}
				if(data_pross_ver_integrita!=null && !data_pross_ver_integrita.equals("")) {
					attrezzatura.setData_prossima_verifica_integrita(format.parse(data_pross_ver_integrita));	
				}
				if(data_ver_interna!=null && !data_ver_interna.equals("")) {
					attrezzatura.setData_verifica_interna(format.parse(data_ver_interna));	
				}
				if(data_pross_ver_interna!=null && !data_pross_ver_interna.equals("")) {
					attrezzatura.setData_prossima_verifica_interna(format.parse(data_pross_ver_interna));	
				}				
				
				session.update(attrezzatura);
				session.getTransaction().commit();
				session.close();
				
				PrintWriter out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Attrezzatura modifica con successo!");
	        	out.print(myObj);
				
			}
			else if(action.equals("scadenzario")) {
				
				String data = request.getParameter("data");
				String tipo_data = request.getParameter("tipo_data");								
				
				List<ClienteDTO> listaClientiFull = (List<ClienteDTO>) request.getSession().getAttribute("listaClienti");
				
				if(listaClientiFull==null) {
					CompanyDTO cmp=(CompanyDTO)request.getSession().getAttribute("usrCompany");
					
					String idCompany=""+cmp.getId();
					listaClientiFull = GestioneAnagraficaRemotaBO.getListaClienti(idCompany);			
				}
							
				List<SedeDTO>listaSediFull  = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");	

				if(listaSediFull== null) {
					listaSediFull = GestioneAnagraficaRemotaBO.getListaSedi();
				}
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
				ArrayList<AttrezzaturaDTO> lista_attrezzature = GestioneAttrezzatureBO.getlistaAttrezzatureData(df.parse(data), tipo_data, session);
				
				 request.getSession().setAttribute("lista_attrezzature",lista_attrezzature);
				
				 if(tipo_data.equals("data_prossima_verifica_integrita")) {
					 tipo_data = tipo_data.substring(0, tipo_data.length()-1) + "à";
				 }
				 request.getSession().setAttribute("tipo_scadenza",tipo_data.replace("_", " "));				 
				 request.getSession().setAttribute("data_scadenza",df2.format(df.parse(data)));				
				 request.getSession().setAttribute("listaClienti",listaClientiFull);
				 request.getSession().setAttribute("listaSedi",listaSediFull);					
	
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaAttrezzatureCalendario.jsp");
		     	dispatcher.forward(request,response);
				
			}

		}
		catch(Exception ex)
    	{
			session.getTransaction().rollback();
        	session.close();
			if(ajax) {
				PrintWriter out = response.getWriter();
				ex.printStackTrace();
	        	
	        	request.getSession().setAttribute("exception", ex);
	        	myObj = ECIException.callExceptionJsonObject(ex);
	        	myObj.addProperty("success", false);
	        	out.print(myObj);
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
