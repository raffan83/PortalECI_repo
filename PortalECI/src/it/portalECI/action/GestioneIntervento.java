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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneStatoInterventoDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.SedeDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneAnagraficaRemotaBO;
import it.portalECI.bo.GestioneAttrezzatureBO;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneComunicazioniBO;
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
			
				List<InterventoDTO> listaInterventi =GestioneInterventoBO.getListaInterventi(idCommessa,session,user, null);	
			
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
				
				CompanyDTO cmp=(CompanyDTO)request.getSession().getAttribute("usrCompany");
				
				String idCompany=""+cmp.getId();				
			
				List<SedeDTO> listaSedi = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");
				if(listaSedi==null) {
						listaSedi = GestioneAnagraficaRemotaBO.getListaSedi();							
				}
				
				List<SedeDTO> lista_sedi_cliente = GestioneAnagraficaRemotaBO.getSediFromCliente(listaSedi, comm.getID_ANAGEN_UTIL());
				
				Gson g = new Gson();
						
		
				JsonArray jarr = new JsonArray();
				JsonObject json = new JsonObject();
				ArrayList<AttrezzaturaDTO> listaAttrezzature =GestioneAttrezzatureBO.getlistaAttrezzatureSede(comm.getID_ANAGEN_UTIL(), comm.getK2_ANAGEN_INDR_UTIL(), false, session);
				SedeDTO sede = GestioneAnagraficaRemotaBO.getSedeFromId(listaSedi, comm.getK2_ANAGEN_INDR_UTIL(), comm.getID_ANAGEN_UTIL());
				request.getSession().setAttribute("tipi_verifica", tipi_verifica);
				request.getSession().setAttribute("categorie_verifica", categorie_verifica);
				request.getSession().setAttribute("listaInterventi", listaInterventi);
				request.getSession().setAttribute("tecnici", users);
				
				for (UtenteDTO utente : users) {
					jarr.add(utente.getUtenteJsonObject(false));	
				}
				json.add("utenti", jarr);
				
				request.getSession().setAttribute("tecnici_json", jarr);
				request.getSession().setAttribute("listaAttrezzature", listaAttrezzature);			
				request.getSession().setAttribute("lista_sedi_cliente",lista_sedi_cliente);
				if(sede!=null && sede.getEsercente()!=null) {
					request.getSession().setAttribute("esercente",sede.getEsercente());	
				}else {
					request.getSession().setAttribute("esercente","");
				}
				
				
				

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneIntervento.jsp");
				dispatcher.forward(request,response);
			}else if(action !=null && action.equals("new")){
		 												
				String id_tecnico = request.getParameter("tecnico");
				List<TipoVerificaDTO> tipoverificalist = new ArrayList<TipoVerificaDTO>();
				List<VerbaleDTO> verbali = new ArrayList<>();

				String[] categoriaTipo=request.getParameterValues("categoriaTipo");
				String[] schedaTecnicaObbligatoria=request.getParameterValues("schedaTecnica");
				String[] listaNote =request.getParameterValues("note");
				String[] listaSedi =request.getParameterValues("sedi");
				String[] listaAttrezzature =request.getParameterValues("attrezzature");
				String[] listaEsercenti =request.getParameterValues("esercenti");
				String[] listaEffVer =request.getParameterValues("effettuazione_verifica");
				String[] listaTipoVer =request.getParameterValues("tipo_verifica");
				String[] listaDescrizioneUtil =request.getParameterValues("descrizione_util");
				

				CommessaDTO comm=(CommessaDTO)request.getSession().getAttribute("commessa");
				
				for( int i = 0; i <= categoriaTipo.length - 1; i++){
					
					String id_tipo=categoriaTipo[i].substring(0, categoriaTipo[i].indexOf("_"));
					String id_categoria=categoriaTipo[i].substring(categoriaTipo[i].indexOf("_")+1, categoriaTipo[i].length());
					TipoVerificaDTO tipoVerificaDTO = GestioneInterventoBO.getTipoVerifica(id_tipo, session); 
					tipoverificalist.add(tipoVerificaDTO);
					Boolean createSkTec=false;
					if(schedaTecnicaObbligatoria!=null) {
						if(schedaTecnicaObbligatoria[i].equals("1")) {
							createSkTec = true;
						}
					}
//					if(schedaTecnicaObbligatoria!=null && schedaTecnicaObbligatoria.length>0) {
//						for( int a = 0; a<= schedaTecnicaObbligatoria.length - 1; a++){
//
//							if(schedaTecnicaObbligatoria[a]!=null && schedaTecnicaObbligatoria[a].equals(categoriaTipo[i])) {
//								schedaTecnicaObbligatoria[a]=null;
//								createSkTec=true;							
//								break;
//							}
//						}
//					}
					
					AttrezzaturaDTO attrezzatura= null;
					
					if(listaAttrezzature!=null && listaAttrezzature[i]!=null && !listaAttrezzature[i].equals("") && !listaAttrezzature[i].equals("0")) {
							attrezzatura = GestioneAttrezzatureBO.getAttrezzaturaFromId(Integer.parseInt(listaAttrezzature[i]), session);
					}
						
					VerbaleDTO verbale =GestioneVerbaleBO.buildVerbale(tipoVerificaDTO.getCodice(), session, createSkTec,listaNote[i],attrezzatura,listaSedi[i], listaEsercenti[i], listaEffVer[i], listaTipoVer[i], listaDescrizioneUtil[i]);
					if(verbale !=null) {
						verbali.add(verbale);
					}else {
						throw new Exception("Questionario inesistente per Codice Verifica : "+tipoVerificaDTO.getCodice());
					}
					
				}
				
				
				UtenteDTO tecnico = GestioneUtenteBO.getUtenteById(id_tecnico, session);
				
				
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
				
				//GestioneComunicazioniBO.sendEmail(intervento.getTecnico_verificatore(),intervento,null, null,0);
				
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
//				String[] categoriaTipo=request.getParameterValues("categoriaTipo");
//				String[] schedaTecnicaObbligatoria=request.getParameterValues("schedaTecnica");				
				
				InterventoDTO intervento = GestioneInterventoBO.getIntervento(idIntervento, session);
						
				if(intervento.getStatoIntervento().getId()!=StatoInterventoDTO.CREATO) {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", "Errore! L'intervento \u00E8 gi\u00E0 stato scaricato o lavorato dal tecnico.");
					out.print(myObj);
					return;
				}
				
//				for(VerbaleDTO verbale : intervento.getVerbali()) {
//					try {
//						if(verbale!=null && verbale.getSchedaTecnica()!=null) {
//							session.delete(verbale.getSchedaTecnica());
//						}
//						session.delete(verbale);
//					}catch (Exception e) {
//						// TODO: handle exception
//					}
//					intervento.setVerbali(null);
//					session.save(intervento);
//				}
//				
//				for( int i = 0; i <= categoriaTipo.length - 1; i++){				
//					String id_tipo=categoriaTipo[i].substring(0, categoriaTipo[i].indexOf("_"));
//					TipoVerificaDTO tipoVerificaDTO = GestioneInterventoBO.getTipoVerifica(id_tipo, session); 
//					tipoverificalist.add(tipoVerificaDTO);
//					
//					VerbaleDTO verbaleTarget=null;
//					
//					//Nuova categoria su update
//					Boolean createSkTec=false;					
//					if(schedaTecnicaObbligatoria!=null && schedaTecnicaObbligatoria.length>0) {
//						for( int a = 0; a<= schedaTecnicaObbligatoria.length - 1; a++){
//
//							if(schedaTecnicaObbligatoria[a]!=null && schedaTecnicaObbligatoria[a].equals(categoriaTipo[i])) {
//								schedaTecnicaObbligatoria[a]=null;
//								createSkTec=true;
//							
//								break;
//							}
//						}
//					}
//											
//					verbaleTarget =GestioneVerbaleBO.buildVerbale(tipoVerificaDTO.getCodice(), session, createSkTec,"",null,"","",null, null, null);
//						
//					if(verbaleTarget ==null) {														
//						myObj.addProperty("success", false);
//						myObj.addProperty("messaggio", "Questionario inesistente per Codice Verifica : "+tipoVerificaDTO.getCodice());
//							
//						out.print(myObj);
//						return;
//					}
//										
//					verbali.add(verbaleTarget);
//					
//				}
				
				UtenteDTO tecnico = GestioneUtenteBO.getUtenteById(id_tecnico, session);							
				if(intervento.getTecnico_verificatore()!=tecnico) {
					intervento.setTecnico_verificatore(tecnico);
				}
				
//				if(!tipoverificalist.isEmpty()) {
//					intervento.setTipo_verifica(tipoverificalist);				
//				}
//				
//				if(!verbali.isEmpty()) {
//					intervento.setVerbali(verbali);
//				}				
				
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
					
					if(idStato== StatoInterventoDTO.ANNULLATO) {
						List<VerbaleDTO> listaVerbali = intervento.getVerbali();
						StatoVerbaleDTO statoVerb = new StatoVerbaleDTO();
						statoVerb.setId(10);
						for (VerbaleDTO verbaleDTO : listaVerbali) {
							verbaleDTO.setStato(statoVerb);
							verbaleDTO.setData_verifica(null);
							verbaleDTO.setData_prossima_verifica(null);
							verbaleDTO.setData_verifica_integrita(null);
							verbaleDTO.setData_prossima_verifica_integrita(null);
							verbaleDTO.setData_verifica_interna(null);
							verbaleDTO.setData_prossima_verifica_interna(null);
							verbaleDTO.setNumeroVerbale(null);
						}
					}
					
					
					
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
			
			else if(action!=null && action.equals("aggiungi_verbale")) {
				
				String id_tecnico = request.getParameter("tecnico");
				String id_intervento = request.getParameter("id_intervento");
				InterventoDTO intervento= GestioneInterventoBO.getIntervento(id_intervento, session);
				
				List<TipoVerificaDTO> tipoverificalist = new ArrayList<TipoVerificaDTO>();
				List<VerbaleDTO> verbali = intervento.getVerbali();

				String[] categoriaTipo=request.getParameterValues("categoriaTipo");
				String[] schedaTecnicaObbligatoria=request.getParameterValues("schedaTecnica");
				String[] listaNote =request.getParameterValues("note");
				String[] listaSedi =request.getParameterValues("sedi");
				String[] listaAttrezzature =request.getParameterValues("attrezzature");
				String[] listaEsercenti =request.getParameterValues("esercenti");
				String[] listaEffVer =request.getParameterValues("effettuazione_verifica");
				String[] listaTipoVer =request.getParameterValues("tipo_verifica");
				String[] listaDescrizioneUtil =request.getParameterValues("descrizione_util");
				

				CommessaDTO comm=(CommessaDTO)request.getSession().getAttribute("commessa");
				
				for( int i = 0; i <= categoriaTipo.length - 1; i++){
					
					String id_tipo=categoriaTipo[i].substring(0, categoriaTipo[i].indexOf("_"));
					String id_categoria=categoriaTipo[i].substring(categoriaTipo[i].indexOf("_")+1, categoriaTipo[i].length());
					TipoVerificaDTO tipoVerificaDTO = GestioneInterventoBO.getTipoVerifica(id_tipo, session); 
					tipoverificalist.add(tipoVerificaDTO);
					Boolean createSkTec=false;
					if(schedaTecnicaObbligatoria!=null) {
						if(schedaTecnicaObbligatoria[i].equals("1")) {
							createSkTec = true;
						}
					}

					AttrezzaturaDTO attrezzatura= null;
					
					if(listaAttrezzature!=null && listaAttrezzature[i]!=null && !listaAttrezzature[i].equals("") && !listaAttrezzature[i].equals("0")) {
							attrezzatura = GestioneAttrezzatureBO.getAttrezzaturaFromId(Integer.parseInt(listaAttrezzature[i]), session);
					}
						
					VerbaleDTO verbale =GestioneVerbaleBO.buildVerbale(tipoVerificaDTO.getCodice(), session, createSkTec,listaNote[i],attrezzatura,listaSedi[i], listaEsercenti[i], listaEffVer[i], listaTipoVer[i], listaDescrizioneUtil[i]);
					if(verbale !=null) {
						verbali.add(verbale);
						
						
						//myObj.add("verbale", verbale.getVerbaleJsonObject());
					}else {
						throw new Exception("Questionario inesistente per Codice Verifica : "+tipoVerificaDTO.getCodice());
					}
					
				}
				
				
				GestioneInterventoBO.update(intervento,session);
					
				myObj.addProperty("success", true);
				//myObj.add("intervento", intervento.getInterventoJsonObject());
			
				out.print(myObj);
				
			}
			else if(action!=null && action.equals("elimina_verbale")){
				
				String id_verbale = request.getParameter("idVerbale");
				String id_intervento = request.getParameter("id_intervento");
				InterventoDTO intervento= GestioneInterventoBO.getIntervento(id_intervento, session);
				
				VerbaleDTO verbale = GestioneVerbaleBO.getVerbale(id_verbale, session);
				if(verbale.getStato().getId()==StatoVerbaleDTO.CREATO) {
					session.delete(verbale);
					intervento.getVerbali().remove(verbale);
				}else {
					StatoVerbaleDTO stato = GestioneStatoVerbaleDAO.getStatoVerbaleById( 10, session);
					verbale.setStato(stato);
					
					verbale.setStato(stato);
					verbale.setData_verifica(null);
					verbale.setData_prossima_verifica(null);
					verbale.setData_verifica_integrita(null);
					verbale.setData_prossima_verifica_integrita(null);
					verbale.setData_verifica_interna(null);
					verbale.setData_prossima_verifica_interna(null);
					verbale.setNumeroVerbale(null);
					
					if (verbale.getSchedaTecnica()!=null) {
						verbale.getSchedaTecnica().setStato(stato);
						verbale.getSchedaTecnica().setStato(stato);
						verbale.getSchedaTecnica().setData_verifica(null);
						verbale.getSchedaTecnica().setData_prossima_verifica(null);
						verbale.getSchedaTecnica().setData_verifica_integrita(null);
						verbale.getSchedaTecnica().setData_prossima_verifica_integrita(null);
						verbale.getSchedaTecnica().setData_verifica_interna(null);
						verbale.getSchedaTecnica().setData_prossima_verifica_interna(null);
						verbale.getSchedaTecnica().setNumeroVerbale(null);
						
						session.update(verbale.getSchedaTecnica());
					}
					session.update(verbale);
				}
				
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Operazione completata con successo");
			
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