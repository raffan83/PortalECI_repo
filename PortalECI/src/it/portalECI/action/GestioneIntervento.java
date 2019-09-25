package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneStatoInterventoDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneUtenteBO;
import it.portalECI.bo.GestioneVerbaleBO;

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
		doPost(request, response);
		
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
		
		UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
		try {												
			
			if(action ==null || action.equals("")){
				String idCommessa=request.getParameter("idCommessa");
			
				//	ArrayList<CommessaDTO> listaCommesse =(ArrayList<CommessaDTO>) request.getSession().getAttribute("listaCommesse");				
			
				CommessaDTO comm=GestioneCommesseBO.getCommessaById(idCommessa);									
				request.getSession().setAttribute("commessa", comm);
			
				List<InterventoDTO> listaInterventi =GestioneInterventoBO.getListaInterventi(idCommessa,session,user);	
			
				if(comm.getSYS_STATO().equals("1CHIUSA")) {
					
					for (InterventoDTO intervento :listaInterventi) {					
						
						try {
							intervento.cambioStatoIntervento(GestioneStatoInterventoDAO.getStatoInterventoById(StatoInterventoDTO.CHIUSO, session));
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
			}else if(action !=null && action.equals("new")){
		 												
				String id_tecnico = request.getParameter("tecnico");
				List<TipoVerificaDTO> tipoverificalist = new ArrayList<TipoVerificaDTO>();
				List<VerbaleDTO> verbali = new ArrayList<>();

				String[] categoriaTipo=request.getParameterValues("categoriaTipo");
				String[] schedaTecnicaObbligatoria=request.getParameterValues("schedaTecnica");
				String[] listaNote =request.getParameterValues("note");
				
				for( int i = 0; i <= categoriaTipo.length - 1; i++){
					
					String id_tipo=categoriaTipo[i].substring(0, categoriaTipo[i].indexOf("_"));
					String id_categoria=categoriaTipo[i].substring(categoriaTipo[i].indexOf("_")+1, categoriaTipo[i].length());
					TipoVerificaDTO tipoVerificaDTO = GestioneInterventoBO.getTipoVerifica(id_tipo, session); 
					tipoverificalist.add(tipoVerificaDTO);
					Boolean createSkTec=false;
					
					if(schedaTecnicaObbligatoria!=null && schedaTecnicaObbligatoria.length>0) {
						for( int a = 0; a<= schedaTecnicaObbligatoria.length - 1; a++){

							if(schedaTecnicaObbligatoria[a]!=null && schedaTecnicaObbligatoria[a].equals(categoriaTipo[i])) {
								schedaTecnicaObbligatoria[a]=null;
								createSkTec=true;							
								break;
							}
						}
					}
					
					VerbaleDTO verbale =GestioneVerbaleBO.buildVerbale(tipoVerificaDTO.getCodice(), session, createSkTec,listaNote[i]);
					if(verbale !=null) {
						verbali.add(verbale);
					}else {
						throw new Exception("Questionario inesistente per Codice Verifica : "+tipoVerificaDTO.getCodice());
					}
					
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
				if(!verbali.isEmpty()) {				
					intervento.setVerbali(verbali);						
				}
				
				String nomeCliente="";
				
				if(comm.getANAGEN_INDR_INDIRIZZO()!=null && comm.getANAGEN_INDR_INDIRIZZO().length()>0){
					nomeCliente=comm.getID_ANAGEN_NOME()+ " - "+ comm.getANAGEN_INDR_INDIRIZZO();
				}else{
					nomeCliente=comm.getID_ANAGEN_NOME()+ " - "+ comm.getINDIRIZZO_PRINCIPALE(); 
				}
				intervento.setCodiceProvincia(comm.getCOD_PROV());
				intervento.setNome_sede(nomeCliente);
				intervento.setIdCommessa(""+comm.getID_COMMESSA());				
				
				try {
					
					intervento.cambioStatoIntervento(GestioneStatoInterventoDAO.getStatoInterventoById(StatoInterventoDTO.CREATO, session));
				}catch(IllegalStateException e) {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", e.getMessage());
					
					out.print(myObj);
					return;
				}
				
				CompanyDTO cmp =(CompanyDTO)request.getSession().getAttribute("usrCompany");
				intervento.setCompany(cmp);
				
				//Genero Instanza Verbali per questo intervento
				
				
				GestioneInterventoBO.save(intervento,session);
					
				myObj.addProperty("success", true);
				myObj.add("intervento", intervento.getInterventoJsonObject());
			
				out.print(myObj);
				
			}else if(action !=null && action.equals("chiudi")){
			 									
				String idIntervento = request.getParameter("idIntervento" );
				InterventoDTO intervento = GestioneInterventoBO.getIntervento(idIntervento, session);
						
				try {
					intervento.cambioStatoIntervento(GestioneStatoInterventoDAO.getStatoInterventoById(StatoInterventoDTO.CHIUSO, session));
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
			}else if(action !=null && action.equals("apri")){			 					
			
				String idIntervento = request.getParameter("idIntervento" );
				InterventoDTO intervento = GestioneInterventoBO.getIntervento(idIntervento, session);
						
				try {
					intervento.cambioStatoIntervento(GestioneStatoInterventoDAO.getStatoInterventoById(StatoInterventoDTO.CREATO, session));
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
			}else if(action !=null && action.equals("update")){			 					
				
				List<TipoVerificaDTO> tipoverificalist = new ArrayList<TipoVerificaDTO>();
				List<VerbaleDTO> verbali = new ArrayList(); 
				
				String id_tecnico = request.getParameter("tecnico");	
				String idIntervento = request.getParameter("idIntervento" );
				String[] categoriaTipo=request.getParameterValues("categoriaTipo");
				String[] schedaTecnicaObbligatoria=request.getParameterValues("schedaTecnica");				
				
				InterventoDTO intervento = GestioneInterventoBO.getIntervento(idIntervento, session);
						
				if(intervento.getStatoIntervento().getId()!=StatoInterventoDTO.CREATO) {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", "Errore! L'intervento \u00E8 gi\u00E0 stato scaricato o lavorato dal tecnico.");
					out.print(myObj);
					return;
				}
				
				for(VerbaleDTO verbale : intervento.getVerbali()) {
					try {
						if(verbale!=null && verbale.getSchedaTecnica()!=null) {
							session.delete(verbale.getSchedaTecnica());
						}
						session.delete(verbale);
					}catch (Exception e) {
						// TODO: handle exception
					}
					intervento.setVerbali(null);
					session.save(intervento);
				}
				
				for( int i = 0; i <= categoriaTipo.length - 1; i++){				
					String id_tipo=categoriaTipo[i].substring(0, categoriaTipo[i].indexOf("_"));
					TipoVerificaDTO tipoVerificaDTO = GestioneInterventoBO.getTipoVerifica(id_tipo, session); 
					tipoverificalist.add(tipoVerificaDTO);
					
					VerbaleDTO verbaleTarget=null;
					
					//Nuova categoria su update
					Boolean createSkTec=false;					
					if(schedaTecnicaObbligatoria!=null && schedaTecnicaObbligatoria.length>0) {
						for( int a = 0; a<= schedaTecnicaObbligatoria.length - 1; a++){

							if(schedaTecnicaObbligatoria[a]!=null && schedaTecnicaObbligatoria[a].equals(categoriaTipo[i])) {
								schedaTecnicaObbligatoria[a]=null;
								createSkTec=true;
							
								break;
							}
						}
					}
											
					verbaleTarget =GestioneVerbaleBO.buildVerbale(tipoVerificaDTO.getCodice(), session, createSkTec,"");
						
					if(verbaleTarget ==null) {														
						myObj.addProperty("success", false);
						myObj.addProperty("messaggio", "Questionario inesistente per Codice Verifica : "+tipoVerificaDTO.getCodice());
							
						out.print(myObj);
						return;
					}
											
					verbali.add(verbaleTarget);
					
				}
				
				UtenteDTO tecnico = GestioneUtenteBO.getUtenteById(id_tecnico, session);							
				if(intervento.getTecnico_verificatore()!=tecnico) {
					intervento.setTecnico_verificatore(tecnico);
				}
				
				if(!tipoverificalist.isEmpty()) {
					intervento.setTipo_verifica(tipoverificalist);				
				}
				
				if(!verbali.isEmpty()) {
					intervento.setVerbali(verbali);
				}				
				
				GestioneInterventoBO.update(intervento,session);
				
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Intervento modificato con successo");
			
				out.print(myObj);
			}else if(action !=null && action.equals("cambioStato")){			 					
			
				String idIntervento = request.getParameter("idIntervento" );
				String stato = request.getParameter("stato" );
				InterventoDTO intervento = GestioneInterventoBO.getIntervento(idIntervento, session);
				
				int idStato;
				if(stato.equals("CHIUSO")) {
					idStato=StatoInterventoDTO.CHIUSO;
				}else if(stato.equals("ANNULLATO")) {
					idStato=StatoInterventoDTO.ANNULLATO;
				}else if (stato.equals("COMPILAZIONE_WEB")){
					idStato=StatoInterventoDTO.COMPILAZIONE_WEB;
				}else {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", "Errore imprevisto durante il passaggio di stato dell'intervento!");
					
					out.print(myObj);
					return;
				}
				
				try {
					if (idStato==StatoInterventoDTO.COMPILAZIONE_WEB) {
						// cambio lo stato di tutti i verbali in "COMPILAZIONE WEB"
						StatoVerbaleDTO statoVerbale = GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.COMPILAZIONE_WEB, session);
						GestioneVerbaleBO.setStatoCompilazioneWeb(intervento, statoVerbale, session);
						//GestioneInterventoBO.setStatoCompilazioneWeb(intervento, session);
					}				
					intervento.cambioStatoIntervento(GestioneStatoInterventoDAO.getStatoInterventoById(idStato, session));
					
				}catch(IllegalStateException e) {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", e.getMessage());
					
					out.print(myObj);
					return;
				}
				
				GestioneInterventoBO.update(intervento,session);			
					
				myObj.addProperty("success", true);
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
				if(ex.getMessage()!=null && !ex.getMessage().isEmpty())
					myObj.addProperty("dettaglio", ex.getMessage());
			}else if(action !=null && action.equals("chiudi")){
				myObj.addProperty("messaggio", "Errore chiusura intervento.");
				if(ex.getMessage()!=null && !ex.getMessage().isEmpty())
					myObj.addProperty("dettaglio", ex.getMessage());
			}
			out.print(myObj);
	   	     
		}		
	}
}