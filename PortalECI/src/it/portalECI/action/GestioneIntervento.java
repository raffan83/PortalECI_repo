package it.portalECI.action;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneInterventoBO;

import it.portalECI.bo.GestioneUtenteBO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class GestioneIntervento
 */
@WebServlet(name = "gestioneIntervento", urlPatterns = { "/gestioneIntervento.do" })
public class GestioneIntervento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneIntervento() {
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
		JsonObject myObj = new JsonObject();
		PrintWriter  out = response.getWriter();
		String action=request.getParameter("action");
	
		try {												
			
			if(action ==null || action.equals("")){
				String idCommessa=request.getParameter("idCommessa");
			
				//	ArrayList<CommessaDTO> listaCommesse =(ArrayList<CommessaDTO>) request.getSession().getAttribute("listaCommesse");				
			
				CommessaDTO comm=GestioneCommesseBO.getCommessaById(idCommessa);									
				request.getSession().setAttribute("commessa", comm);
			
				List<InterventoDTO> listaInterventi =GestioneInterventoBO.getListaInterventi(idCommessa,session);	
			
				if(comm.getSYS_STATO().equals("1CHIUSA")) {
					
					for (InterventoDTO intervento :listaInterventi) {					
						
						try {
							intervento.cambioStatoIntervento(StatoInterventoDTO.CHIUSO);
						}catch(IllegalStateException e) {
							/*myObj.addProperty("success", false);
							myObj.addProperty("messaggio", e.getMessage());
							
							out.print(myObj);
							return;*/
						}	
						GestioneInterventoBO.update(intervento, session);
					}
				}
						
				List<UtenteDTO> users = GestioneUtenteBO.getTecnici("2", session);
				ArrayList<TipoVerificaDTO> tipi_verifica = GestioneInterventoBO.getTipoVerifica(session);
				ArrayList<CategoriaVerificaDTO> categorie_verifica = GestioneInterventoBO.getCategoriaVerifica(session);
			
				request.getSession().setAttribute("tipi_verifica", tipi_verifica);
				request.getSession().setAttribute("categorie_verifica", categorie_verifica);
				request.getSession().setAttribute("listaInterventi", listaInterventi);
				request.getSession().setAttribute("tecnici", users);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneIntervento.jsp");
				dispatcher.forward(request,response);
			}
			
			if(action !=null && action.equals("new")){
		 												
				String id_tecnico = request.getParameter("tecnico");
				Set<CategoriaVerificaDTO> categorielist = new HashSet<CategoriaVerificaDTO>();
				Set<TipoVerificaDTO> tipoverificalist = new HashSet<TipoVerificaDTO>();

				String[] categoriaTipo=request.getParameterValues("categoriaTipo");
			
				for( int i = 0; i <= categoriaTipo.length - 1; i++){
					
					String id_tipo=categoriaTipo[i].substring(0, categoriaTipo[i].indexOf("_"));
					String id_categoria=categoriaTipo[i].substring(categoriaTipo[i].indexOf("_")+1, categoriaTipo[i].length());
		
					tipoverificalist.add(GestioneInterventoBO.getTipoVerifica(id_tipo, session));
					categorielist.add(GestioneInterventoBO.getCategoriaVerifica(id_categoria, session));
				}
				
				UtenteDTO tecnico = GestioneUtenteBO.getUtenteById(id_tecnico, session);
				
				CommessaDTO comm=(CommessaDTO)request.getSession().getAttribute("commessa");
				InterventoDTO intervento= new InterventoDTO();
				intervento.setDataCreazione(Utility.getActualDateSQL());
				intervento.setTecnico_verificatore(tecnico);
				intervento.setUser((UtenteDTO)request.getSession().getAttribute("userObj"));
				intervento.setIdSede(comm.getK2_ANAGEN_INDR());
				intervento.setId_cliente(comm.getID_ANAGEN());
					
				if(!tipoverificalist.isEmpty()) {
					intervento.setTipo_verifica(tipoverificalist);				
				}
				
				String nomeCliente="";
				
				if(comm.getANAGEN_INDR_INDIRIZZO()!=null && comm.getANAGEN_INDR_INDIRIZZO().length()>0){
					nomeCliente=comm.getID_ANAGEN_NOME()+ " - "+ comm.getANAGEN_INDR_INDIRIZZO();
				}else{
					nomeCliente=comm.getID_ANAGEN_NOME()+ " - "+ comm.getINDIRIZZO_PRINCIPALE(); 
				}
			
				intervento.setNome_sede(nomeCliente);
				intervento.setIdCommessa(""+comm.getID_COMMESSA());				
				
				try {
					intervento.cambioStatoIntervento(StatoInterventoDTO.CREATO);
				}catch(IllegalStateException e) {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", e.getMessage());
					
					out.print(myObj);
					return;
				}
				
				CompanyDTO cmp =(CompanyDTO)request.getSession().getAttribute("usrCompany");
				intervento.setCompany(cmp);
								
				GestioneInterventoBO.save(intervento,session);				
					
				myObj.addProperty("success", true);
				myObj.add("intervento", intervento.getInterventoJsonObject());
			
				out.print(myObj);
				
			}
			if(action !=null && action.equals("chiudi")){
			 									
				String idIntervento = request.getParameter("idIntervento" );
				InterventoDTO intervento = GestioneInterventoBO.getIntervento(idIntervento, session);
						
				try {
					intervento.cambioStatoIntervento(StatoInterventoDTO.CHIUSO);
				}catch(IllegalStateException e) {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", e.getMessage());
					
					out.print(myObj);
					return;
				}
				
				GestioneInterventoBO.update(intervento,session);
				
				Gson gson = new Gson();
			
				String jsonInString = gson.toJson(intervento);
					
				myObj.addProperty("success", true);
				myObj.addProperty("intervento", jsonInString);
				myObj.addProperty("messaggio", "Intervento chiuso");
			
				out.print(myObj);
			}
			if(action !=null && action.equals("apri")){			 					
			
				String idIntervento = request.getParameter("idIntervento" );
				InterventoDTO intervento = GestioneInterventoBO.getIntervento(idIntervento, session);
						
				try {
					intervento.cambioStatoIntervento(StatoInterventoDTO.CREATO);
				}catch(IllegalStateException e) {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", e.getMessage());
					
					out.print(myObj);
					return;
				}
				
				GestioneInterventoBO.update(intervento,session);
				
				Gson gson = new Gson();
						
				String jsonInString = gson.toJson(intervento);
					
				myObj.addProperty("success", true);
				myObj.addProperty("intervento", jsonInString);
				myObj.addProperty("messaggio", "Intervento aperto");
			
				out.print(myObj);
			}
	
			session.getTransaction().commit();
			session.close();	
		
		}catch (Exception ex) {	
			session.getTransaction().rollback();
			ex.printStackTrace(); 
		  
			myObj.addProperty("success", false);	
			if(action !=null && action.equals("new")){
				myObj.addProperty("messaggio", "Errore creazione intervento.");
			}
			if(action !=null && action.equals("chiudi")){
				myObj.addProperty("messaggio", "Errore chiusura intervento.");
			}
			out.print(myObj);
	   	     
		}		
	}
}