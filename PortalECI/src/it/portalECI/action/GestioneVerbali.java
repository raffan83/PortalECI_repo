package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.arubapec.arubasignservice.ArubaSignService;
import it.arubapec.arubasignservice.TypeOfTransportNotImplementedException;
import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DAO.GestioneInterventoDAO;
import it.portalECI.DAO.GestioneRispostaVerbaleDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.GestioneStoricoModificheDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.ColonnaTabellaVerbaleDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.ComuneDTO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.DomandaVerbaleDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.OpzioneRispostaVerbaleDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaVerbaleDTO;
import it.portalECI.DTO.RispostaSceltaVerbaleDTO;
import it.portalECI.DTO.RispostaTabellaVerbaleDTO;
import it.portalECI.DTO.RispostaTestoVerbaleDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;
import it.portalECI.DTO.SedeDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.StoricoModificheDTO;
import it.portalECI.DTO.StrumentoVerificatoreDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneAnagraficaRemotaBO;
import it.portalECI.bo.GestioneAttrezzatureBO;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneComunicazioniBO;
import it.portalECI.bo.GestioneQuestionarioBO;
import it.portalECI.bo.GestioneVerbaleBO;


/**
 * Servlet implementation class GestioneIntervento
 */
@WebServlet(name = "gestioneVerbale", urlPatterns = { "/gestioneVerbale.do" })
public class GestioneVerbali extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneVerbali() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8"); 
		
		JsonObject myObj = new JsonObject();
		PrintWriter  out = response.getWriter();
		
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		try {
			VerbaleDTO verbale = GestioneVerbaleBO.getVerbale(request.getParameter("idVerbale"),session); 
			
			String action = request.getParameter("action");
			String currentState = request.getParameter("currentState");
					
			Enumeration<String> parameterNames = request.getParameterNames(); //lista id elementi modificati

			ArrayList<String> listaFormulaAggiornate=new ArrayList<String>();
		
			while (parameterNames.hasMoreElements()) {
				String paramName = parameterNames.nextElement();

				String id="";
				
				if(paramName.equals("strumento_verificatore")) {
					
					String id_strumento = request.getParameter("strumento_verificatore");
					if(id_strumento!=null && !id_strumento.equals("") && !id_strumento.equals("0")) {
						verbale.setStrumento_verificatore(new StrumentoVerificatoreDTO(Integer.parseInt(id_strumento)));
						session.update(verbale);
					}else {
						verbale.setStrumento_verificatore(null);
						session.update(verbale);
					}
				}
				
				if(paramName.equals("data_verifica")) {
					
					String data_verifica = request.getParameter("data_verifica");
				
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
					if(data_verifica!=null && !data_verifica.equals("")) {
						verbale.setData_verifica(df.parse(data_verifica));
						session.update(verbale);
						if(verbale.getAttrezzatura()!=null) {
							verbale.getAttrezzatura().setData_verifica_funzionamento(df.parse(data_verifica));						
							session.update(verbale.getAttrezzatura());
						}
					}
					
				}
				
				if(paramName.equals("data_prossima_verifica_verb")) {
					
					String data_prossima_verifica = request.getParameter("data_prossima_verifica_verb");
				
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					if(data_prossima_verifica!=null && !data_prossima_verifica.equals("")) {
						verbale.setData_prossima_verifica(df.parse(data_prossima_verifica));
						if(verbale.getAttrezzatura()!=null) {
							verbale.getAttrezzatura().setData_prossima_verifica_funzionamento(df.parse(data_prossima_verifica));
							session.update(verbale.getAttrezzatura());
						}						
						session.update(verbale);						
					}
					
				}
				if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getTipo_attivita().equals("GVR") && paramName.equals("data_verifica_integrita_verb")) {
					
					String data_verifica_integrita = request.getParameter("data_verifica_integrita_verb");
				
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
					if(data_verifica_integrita!=null && !data_verifica_integrita.equals("")) {
						verbale.setData_verifica_integrita(df.parse(data_verifica_integrita));
						if(verbale.getAttrezzatura()!=null) {
							verbale.getAttrezzatura().setData_verifica_integrita(df.parse(data_verifica_integrita));
							session.update(verbale.getAttrezzatura());
						}
						session.update(verbale);
						
					}
					
				}
				if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getTipo_attivita().equals("GVR") && paramName.equals("data_prossima_verifica_integrita_verb")) {
					
					String data_prossima_verifica_integrita = request.getParameter("data_prossima_verifica_integrita_verb");
				
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					if(data_prossima_verifica_integrita!=null && !data_prossima_verifica_integrita.equals("")) {
						verbale.setData_prossima_verifica_integrita(df.parse(data_prossima_verifica_integrita));
						
						if(verbale.getAttrezzatura()!=null) {
							verbale.getAttrezzatura().setData_prossima_verifica_integrita(df.parse(data_prossima_verifica_integrita));
							session.update(verbale.getAttrezzatura());
						}
						session.update(verbale);
						
					}
					
				}
				if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getTipo_attivita().equals("GVR") && paramName.equals("data_verifica_interna_verb")) {
					
					String data_verifica_interna = request.getParameter("data_verifica_interna_verb");
				
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					if(data_verifica_interna!=null && !data_verifica_interna.equals("")) {
						verbale.setData_verifica_interna(df.parse(data_verifica_interna));
						if(verbale.getAttrezzatura()!=null) {
							verbale.getAttrezzatura().setData_verifica_interna(df.parse(data_verifica_interna));
							session.update(verbale.getAttrezzatura());	
						}
						
						session.update(verbale);
						
					}
					
					
				}
				if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getTipo_attivita().equals("GVR") && paramName.equals("data_prossima_verifica_interna_verb")) {
					
					String data_prossima_verifica_interna = request.getParameter("data_prossima_verifica_interna_verb");
				
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					if(data_prossima_verifica_interna!=null && !data_prossima_verifica_interna.equals("")) {
						verbale.setData_prossima_verifica_interna(df.parse(data_prossima_verifica_interna));
						if(verbale.getAttrezzatura()!=null) {
							verbale.getAttrezzatura().setData_prossima_verifica_interna(df.parse(data_prossima_verifica_interna));
							session.update(verbale.getAttrezzatura());
						}
						session.update(verbale);
						
					}
					
				}
				
				if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getTipo_attivita().equals("GVR") && paramName.equals("tipo_verifica_gvr")) {
					
					String tipo_verifica_gvr = request.getParameter("tipo_verifica_gvr");
			
					verbale.setTipo_verifica_gvr(Integer.parseInt(tipo_verifica_gvr));
					session.update(verbale);
					
					
				}				


				if(paramName.equals("check_sede")) {					

					String check_sede = request.getParameter("check_sede");
					
					AttrezzaturaDTO attrezzatura = verbale.getAttrezzatura();	
					
					if(attrezzatura!=null) {
						if(check_sede!=null && check_sede.equals("1")) {
							
							String presso = request.getParameter("presso");
							String comune = request.getParameter("comune");
							String indirizzo = request.getParameter("indirizzo");
							String cap = request.getParameter("cap");
							String provincia = request.getParameter("provincia");
							String regione = request.getParameter("regione");
							
							attrezzatura.setPresso_div(presso);
							attrezzatura.setIndirizzo_div(indirizzo);
							attrezzatura.setComune_div(comune);
							attrezzatura.setCap_div(cap);
							attrezzatura.setProvincia_div(provincia);
							attrezzatura.setRegione_div(regione);					
						}else {
							attrezzatura.setIndirizzo_div(null);
							attrezzatura.setComune_div(null);
							attrezzatura.setCap_div(null);
							attrezzatura.setProvincia_div(null);
							attrezzatura.setRegione_div(null);	
						}
						session.update(attrezzatura);
					}
					
										
				}
				
				if(paramName.equals("esito")) {
					
					 String esito = request.getParameter("esito");
					 String descrizione_sospensione = request.getParameter("descrizione_sospensione");
					 
					 
					 verbale.setEsito(esito);					 	
										 
					 
					if(!esito.equals("S")) {
						verbale.setDescrizione_sospensione(null);
						session.update(verbale);	
					}else {
						
						GestioneVerbaleBO.cambioStato( verbale, GestioneStatoVerbaleDAO.getStatoVerbaleById( StatoVerbaleDTO.SOSPESO, session) , session);
						verbale.setDescrizione_sospensione(descrizione_sospensione);
						session.update(verbale);	
						myObj.addProperty("success", true);
						myObj.addProperty("messaggio", "Stato modificato con successo");
			
						out.print(myObj);
						return;
					}
				
									
				}
				if(paramName.equals("frequenza")) {
					
					String frequenza = request.getParameter("frequenza");
					
					if(frequenza!=null && !frequenza.equals("")) {
						verbale.setFrequenza(Integer.parseInt(frequenza));
						session.update(verbale);
					}
					
				}
				if(paramName.equals("check_motivo")) {
					
					String motivo = request.getParameter("check_motivo");
					
					if(motivo!=null && !motivo.equals("")) {
						verbale.setMotivo_verifica(Integer.parseInt(motivo));						
						
						if(Integer.parseInt(motivo)>1) {
							verbale.setFrequenza(0);
						}
						session.save(verbale);
					}
					
				}
				if(paramName.equals("tipologia_verifica")) {
					
					String tipologia_verifica = request.getParameter("tipologia_verifica");
					
					if(tipologia_verifica!=null && !tipologia_verifica.equals("")) {
						verbale.setTipologia_verifica(Integer.parseInt(tipologia_verifica));
						session.save(verbale);
					}
					
				}
				if(paramName.equals("ore_uomo")) {
					
					String ore_uomo = request.getParameter("ore_uomo");
					
					if(ore_uomo!=null && !ore_uomo.equals("")) {
						verbale.setOre_uomo(ore_uomo);
						session.save(verbale);
					}
					
				}
				if(paramName.equals("matricola_vie")) {
					
					String matricola_vie = request.getParameter("matricola_vie");
					
					if(matricola_vie!=null && !matricola_vie.equals("")) {
						verbale.setMatricola_vie(matricola_vie);
						session.save(verbale);
					}
					
				}
				

				if(paramName.equals("data_fine_verifica")) {
					
					String data_fine_verifica = request.getParameter("data_fine_verifica");
				
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
					if(data_fine_verifica!=null && !data_fine_verifica.equals("")) {
						verbale.setData_fine_verifica(df.parse(data_fine_verifica));					
						session.update(verbale);					
					}
					
				}
				// RISPOSTA FORMULA
				if(paramName.contains("value1") || paramName.contains("value2") || paramName.contains("responseValue")) {				
					id=paramName.replaceAll("value1", "").replaceAll("value2", "").replaceAll("responseValue", "");
									
					if(!listaFormulaAggiornate.contains(id)) {
						listaFormulaAggiornate.add(id);
						String value1=request.getParameter("value1"+id);
						String value2=request.getParameter("value2"+id);
						String responseValue=request.getParameter("responseValue"+id);
				
						RispostaFormulaVerbaleDTO rispostaFormula = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaFormulaVerbaleDTO.class, Integer.parseInt(id), session);
						
						String val1 = rispostaFormula.getValue1(); 
						String val2 = rispostaFormula.getValue2(); 
						String respVal = rispostaFormula.getResponseValue();
						
						if(val1 == null) val1 = new String();
						if(val2 == null) val2 = new String();
						if(respVal == null) respVal = new String();
						
						if(!val1.equals(value1) || !val2.equals(value2) || !respVal.equals(responseValue)  ) {
							
							if (val1.isEmpty()) val1 = "null";
							if (val2.isEmpty()) val2 = "null";						
							if (respVal.isEmpty()) respVal = "null";
							//if (rispostaFormula.getValue1().isEmpty()) val1 = "null";
							//if (rispostaFormula.getValue2().isEmpty()) val2 = "null";						
							//if (rispostaFormula.getResponseValue().isEmpty()) respVal = "null";
							
							//se stiamo compilando il verbale sull'app WEB non dobbiamo salvare lo storico
							// lo storico è gestito solo in caso di verbale DA VERIFICARE o stati successivi
							if (currentState == null || !currentState.equals("compilazioneWeb")) {
								addStorico(verbale, 
										val1+"|"+ val2+"|"+respVal , 
										null, 
										rispostaFormula.getId(), 
										StoricoModificheDTO.UPDATE, 
										request, 
										session);
							}
						}
						
						rispostaFormula.setValue1(value1);
						rispostaFormula.setValue2(value2);
						rispostaFormula.setResponseValue(responseValue);
						GestioneRispostaVerbaleDAO.save(rispostaFormula, session);
					}
				
				// RISPOSTA MULTIPLA
				}else if(paramName.contains("options")) {		
					Boolean change=false;					
					String oldCheck="";
					String idrispostaSceltaVerbale=paramName.replaceAll("options", "");
			
					Set<OpzioneRispostaVerbaleDTO> listaOpzioni= GestioneRispostaVerbaleDAO.getRispostaSceltaVerbaleDTO(Integer.parseInt(idrispostaSceltaVerbale), session).getOpzioni();
				
					String[] listaid = request.getParameterValues(paramName);
				
					for (OpzioneRispostaVerbaleDTO s : listaOpzioni) {
																
						Boolean value=false;
						if(Arrays.asList(listaid).contains(String.valueOf(s.getId()))) {
							value=true;
						}
						
						if(s.getChecked()!=value) {
							change=true;
						}
						
						if (s.getChecked() == true) {
							oldCheck += s.getId()+"|";
						}
						
						OpzioneRispostaVerbaleDTO opzioneRispostaVerbaleDTO = GestioneRispostaVerbaleDAO.getOpzioneVerbale(s.getId(), session);
						opzioneRispostaVerbaleDTO.setChecked(value);
						GestioneRispostaVerbaleDAO.saveOpzioneVerbale(opzioneRispostaVerbaleDTO, session);
					}

					if(change) {
						if (currentState == null || !currentState.equals("compilazioneWeb")) {
							addStorico(verbale, oldCheck, null, Integer.parseInt(idrispostaSceltaVerbale), StoricoModificheDTO.UPDATE, request, session);
						}
					}						
				
				// RISPOSTA TESTO
				}else if(isInt(paramName)){			
					
					String testo=request.getParameter(paramName);

					RispostaTestoVerbaleDTO rispostaTesto = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaTestoVerbaleDTO.class, Integer.parseInt(paramName), session);
					String rispostaTestoVal = rispostaTesto.getResponseValue();

					if (rispostaTestoVal == null) rispostaTestoVal = new String();
					
						if(!rispostaTestoVal.equals(testo)) {
							if (currentState == null || !currentState.equals("compilazioneWeb")) {
								addStorico(verbale, 
										rispostaTestoVal, 
										null, 
										rispostaTesto.getId(), 
										StoricoModificheDTO.UPDATE, 
										request, 
										session);
							}
						}
					
					rispostaTesto.setResponseValue(testo);
					GestioneRispostaVerbaleDAO.save(rispostaTesto, session);

				}
				
			}
			String[] idRisposteEliminate = request.getParameterValues("risposte_eliminate");
			if(idRisposteEliminate!=null) {
				Map<String,String> storici = new HashMap<String,String>();
				for(String idRispString:idRisposteEliminate) {
					Integer idRisp = Integer.parseInt(idRispString);
					RispostaVerbaleDTO risposta = (RispostaVerbaleDTO)session.get(RispostaVerbaleDTO.class,idRisp);
					if(risposta==null) continue;
					String  valoreStorico ="<td>";
					switch (risposta.getTipo()) {
					case RispostaVerbaleDTO.TIPO_TESTO:
						RispostaTestoVerbaleDTO rispT = (RispostaTestoVerbaleDTO)session.get(RispostaTestoVerbaleDTO.class, idRisp);
						valoreStorico=valoreStorico.concat(rispT.toString());
						break;
					case RispostaVerbaleDTO.TIPO_SCELTA:
						RispostaSceltaVerbaleDTO rispS = (RispostaSceltaVerbaleDTO)session.get(RispostaSceltaVerbaleDTO.class, idRisp);
						valoreStorico=valoreStorico.concat(rispS.toString());
						break;
					case RispostaVerbaleDTO.TIPO_FORMULA:
						RispostaFormulaVerbaleDTO rispF = (RispostaFormulaVerbaleDTO) session.get(RispostaFormulaVerbaleDTO.class, idRisp);
						valoreStorico=valoreStorico.concat(rispF.toString());
					}
					valoreStorico=valoreStorico.concat("</td>");
					
					String id_risposta_tabella = request.getParameter("id_risposta_tabella"+idRispString);
					String riga = request.getParameter("riga_risposta"+idRispString);
					String pointer = id_risposta_tabella+"_"+riga;
					if(storici.get(pointer)==null) 
						storici.put(pointer, valoreStorico); 
					else 
						storici.put(pointer,storici.get(pointer).concat(valoreStorico));
					RispostaTabellaVerbaleDTO rispostaTabella = (RispostaTabellaVerbaleDTO) session.get(RispostaTabellaVerbaleDTO.class, Integer.parseInt(id_risposta_tabella));
					for (ColonnaTabellaVerbaleDTO colonna: rispostaTabella.getColonne()) {
						colonna.getRisposte().remove(risposta);
					}
					session.save(rispostaTabella);
				    session.delete(risposta);
					
				}
			    Iterator<Map.Entry<String,String>> it = storici.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
			        if(!pair.getValue().replaceAll("<td>", "").replaceAll("</td>", "").isEmpty()) {
				        String pointer = pair.getKey();
				        String idRispostaTabellaId = pointer.split("_")[0];
				        if (currentState == null || !currentState.equals("compilazioneWeb")) {
				        	addStorico(verbale, pair.getValue(), "riga", Integer.parseInt(idRispostaTabellaId), StoricoModificheDTO.DELETE, request, session);
			        
				        }
			        }
			    }
			}	
		
			for (DomandaVerbaleDTO domanda:verbale.getDomandeVerbale()) {
				int valueRicercato=domanda.getRisposta().getId();

				if(domanda.getRisposta().getTipo().equals(RispostaVerbaleDTO.TIPO_SCELTA)) {
					if(!request.getParameterMap().containsKey("options"+String.valueOf(valueRicercato))){			
						// se stiamo solo salvando il verbale nello stato COMPILAZIONE WEB non facciamo controlli sulle domande obbligatorie
						// il controllo verrà fatto quando si proverà a confermare il verbale in COMPILAZIONE WEB oppure quando si modificherà il verbale
						if (action == null || !action.equals("salvaRisposteCompWeb")) {
							if(domanda.getDomandaQuestionario().getObbligatoria()) {
								myObj.addProperty("success", false);
								myObj.addProperty("messaggio", "La domanda '"+domanda.getDomandaQuestionario().getTesto()+"' &egrave; obbligatoria.");
						
								out.print(myObj);
								return;
							}
						}
					
						Set<OpzioneRispostaVerbaleDTO> listaOpzioni= GestioneRispostaVerbaleDAO.getRispostaSceltaVerbaleDTO(valueRicercato, session).getOpzioni();
					
						for (OpzioneRispostaVerbaleDTO s : listaOpzioni) {
							OpzioneRispostaVerbaleDTO opzioneRispostaVerbaleDTO = GestioneRispostaVerbaleDAO.getOpzioneVerbale(s.getId(), session);
							opzioneRispostaVerbaleDTO.setChecked(false);
							GestioneRispostaVerbaleDAO.saveOpzioneVerbale(opzioneRispostaVerbaleDTO, session);
						}		
					}
				} else if(domanda.getRisposta().getTipo().equals(RispostaVerbaleDTO.TIPO_TESTO)) {
					if (action != null && action.equals("confermaRisposteCompWeb")) {
						if(domanda.getDomandaQuestionario().getObbligatoria()) {
							String rispostaTesto = request.getParameter(String.valueOf(valueRicercato));
							if (rispostaTesto == null || rispostaTesto.equals("")) {
								myObj.addProperty("success", false);
								myObj.addProperty("messaggio", "La domanda '"+domanda.getDomandaQuestionario().getTesto()+"' &egrave; obbligatoria.");
						
								out.print(myObj);
								return;
							}
						}
					}
				} else if(domanda.getRisposta().getTipo().equals(RispostaVerbaleDTO.TIPO_FORMULA)) {
					if (action != null && action.equals("confermaRisposteCompWeb")) {
						if(domanda.getDomandaQuestionario().getObbligatoria()) {
							String value1 = request.getParameter("value1"+String.valueOf(valueRicercato));
							String value2 = request.getParameter("value2"+String.valueOf(valueRicercato));	
							String responseValue = request.getParameter("responseValue"+String.valueOf(valueRicercato));	
							if (value1 == null || value1.equals("")
								|| value2 == null || value2.equals("")
								|| responseValue == null || responseValue.equals("")) {
								myObj.addProperty("success", false);
								myObj.addProperty("messaggio", "La domanda '"+domanda.getDomandaQuestionario().getTesto()+"' &egrave; obbligatoria.");
						
								out.print(myObj);
								return;
							}
						}
					}
				}
			}
			
			
			if (action != null && action.equals("confermaRisposteCompWeb")) {
				
				verbale.setData_conferma(new Date());
				GestioneVerbaleBO.cambioStato( verbale, GestioneStatoVerbaleDAO.getStatoVerbaleById( StatoVerbaleDTO.DA_VERIFICARE, session) , session);				

				if(verbale.getIntervento()!=null && verbale.getIntervento().getTecnico_verificatore()!=null && verbale.getIntervento().getTecnico_verificatore().getEMail()!=null) {
					ArrayList<UtenteDTO> lista_utenti = GestioneComunicazioniBO.getListaUtentiComunicazione(verbale.getCodiceCategoria(), session);
					String destinatari = "";
					for (UtenteDTO utenteDTO : lista_utenti) {
						destinatari = destinatari+utenteDTO.getEMail()+";";
					}				
										
					GestioneComunicazioniBO.sendEmailVerbale(verbale, destinatari, verbale.getIntervento().getTecnico_verificatore().getEMail(), StatoVerbaleDTO.DA_VERIFICARE);
				}
				
			}
			
			myObj.addProperty("success", true);
			myObj.addProperty("messaggio", "Stato modificato con successo");
			
		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", false);
			myObj.addProperty("messaggio", "Errore imprevisto durante il salvataggio delle modifiche.");
			
		}finally {
			session.getTransaction().commit();
			session.close();
		}
		
		
		
	
		out.print(myObj);
	}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
		String idVerbale=request.getParameter("idVerbale");
		VerbaleDTO verbale = null;
		if(request.getParameter("idVerbale") != null && (String)request.getParameter("idVerbale")!="" ) {
			verbale = GestioneVerbaleBO.getVerbale(idVerbale, session);
		}
		//verbale.setNumeroVerbale("TMP_01");
		JsonObject myObj = new JsonObject();
		PrintWriter  out = response.getWriter();
		String action=request.getParameter("action");
		
		if(action !=null && action.equals("cambioStato")){	
			
			String stato = request.getParameter("stato" );
			if(Boolean.parseBoolean(request.getParameter("all"))) {
				if(verbale.getSchedaTecnica()!=null && Integer.parseInt(stato) != StatoVerbaleDTO.COMPILAZIONE_WEB)
					GestioneVerbaleBO.cambioStato( verbale.getSchedaTecnica(), GestioneStatoVerbaleDAO.getStatoVerbaleById( Integer.parseInt(stato), session) , session);
				
			}
			
			GestioneVerbaleBO.cambioStato( verbale, GestioneStatoVerbaleDAO.getStatoVerbaleById( Integer.parseInt(stato), session) , session);
			if(stato.equals("5")) {
				verbale.setFirmato(0);
				verbale.setControfirmato(0);
				session.update(verbale);
			}
			
			if(stato.equals("6")) {
				try {
					if(verbale.getIntervento().getTecnico_verificatore().getEMail()!=null && user.getEMail()!=null) {
						GestioneComunicazioniBO.sendEmailVerbale(verbale, verbale.getIntervento().getTecnico_verificatore().getEMail(), user.getEMail(), 6);
						
						verbale.setFirmato(0);
						verbale.setControfirmato(0);
						
					}
				}catch (Exception e) {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", "Errore nell'invio dell'e-mail");
				}
			}
			
			myObj.addProperty("success", true);
			myObj.addProperty("messaggio", "Stato modificato con successo");			
			out.print(myObj);
		} else if(action !=null && action.equals("generaCertificato")) {
			QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(verbale.getQuestionarioID(),session);
			try {
				verbale.setData_approvazione(new Date());
				verbale.setResponsabile_approvatore(user);
				session.update(verbale);
				
				File certificato = GestioneVerbaleBO.getPDFVerbale(verbale, questionario, session);
				if(certificato != null) {
					byte[] pdfArray = loadFileForBase64(certificato);
					if(pdfArray.length == 0) {
						myObj.addProperty("success", false);
						myObj.addProperty("messaggio","Certificato troppo grande per essere generato!");						
					} else {
						byte[] encoded = Base64.encodeBase64(pdfArray);
						String pdfBytes = new String(encoded);
						
						if(verbale.getIntervento().getTecnico_verificatore().getEMail()!=null && user.getEMail()!=null) {
							GestioneComunicazioniBO.sendEmailVerbale(verbale, verbale.getIntervento().getTecnico_verificatore().getEMail(), user.getEMail(), 5);
						}
						
						myObj.addProperty("pdfString", pdfBytes);
						myObj.addProperty("success", true);
						myObj.addProperty("messaggio","Certificato creato con successo!");
						
					}
				} else {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", "Non &egrave; stato possibile generare il Certificato. Problema di connessione.");						
				}
			} catch (Exception e) {
				e.printStackTrace();
				myObj.addProperty("success", false);
				myObj.addProperty("messaggio", "Non &egrave; stato possibile recuperare il Certificato. Problema di connessione.");
			}
			out.print(myObj);
		} else if(action !=null && action.equals("generaSchedaTecnica")) {
			QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(verbale.getQuestionarioID(),session);
			try {
				File certificato = GestioneVerbaleBO.getPDFVerbale(verbale, questionario, session);
				if(certificato != null) {
					byte[] pdfArray = loadFileForBase64(certificato);
					if(pdfArray.length == 0) {
						myObj.addProperty("success", false);
						myObj.addProperty("messaggio","Scheda Tecnica troppo grande per essere generato!");						
					} else {
						byte[] encoded = Base64.encodeBase64(pdfArray);
						String pdfBytes = new String(encoded);
						myObj.addProperty("pdfString", pdfBytes);
						myObj.addProperty("success", true);
						myObj.addProperty("messaggio","Scheda Tecnica creato con successo!");
					}
				} else {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", "Non &egrave; stato possibile generare la Scheda Tecnica.  Problema di connessione.");						
				}
			} catch (Exception e) {
				myObj.addProperty("success", false);
				myObj.addProperty("messaggio", "Non &egrave; stato possibile recuperare la Scheda Tecnica. Problema di connessione.");
			}
			out.print(myObj);
		} else if(action !=null && action.equals("visualizzaDocumento")) {
			String idDoc=request.getParameter("idDoc");
			if(idDoc != null && idDoc != "") {
				DocumentoDTO doc = GestioneDocumentoDAO.getDocumento(idDoc, session);					
				File docPdf =  new File(Costanti.PATH_CERTIFICATI+doc.getFilePath());
				if(docPdf.length() > 0) {
					byte[] pdfArray = loadFileForBase64(docPdf);
					if(pdfArray.length == 0) {
						myObj.addProperty("success", false);
						myObj.addProperty("messaggio","Documento troppo grande per essere generato!");						
					} else {
						byte[] encoded = Base64.encodeBase64(pdfArray);
						String pdfBytes = new String(encoded);
						myObj.addProperty("pdfString", pdfBytes);
						myObj.addProperty("success", true);
						myObj.addProperty("messaggio","Documento scaricato con successo!");
					}
				} else {
					myObj.addProperty("success", false);
					myObj.addProperty("messaggio", "Non &egrave; stato possibile recuperare il documento.");					
				}		
			} else {
				myObj.addProperty("success", false);
				myObj.addProperty("messaggio", "Non &egrave; stato possibile trovare il documento. Generare di nuovo il Pdf.");						
			}
			out.print(myObj);
		} 
		
		else if(action!=null && action.equals("firma_verbale")) {
			
			String pin = request.getParameter("pin");	
			String controfirma = request.getParameter("controfirma");
				
			if(user.getPin_firma()!=null && pin.equals(user.getPin_firma())) {

				Set<DocumentoDTO> listaDocumenti = verbale.getDocumentiVerbale();
				
				Iterator iterator = listaDocumenti.iterator();
				
				while(iterator.hasNext()) {
					DocumentoDTO doc = (DocumentoDTO) iterator.next();
					
					if(!doc.getInvalid()) {						
						try {
							
							//myObj = ArubaSignService.sign(user.getId_firma(), doc, controfirma);
							myObj = ArubaSignService.signDocumentoPades(user, "", doc, controfirma);
							
						//	GestioneVerbaleBO.addSignature(doc);
							
							if(controfirma!=null && controfirma.equals("1")) {
								verbale.setControfirmato(1);
							}else {
								verbale.setFirmato(1);
									
							}
							
							session.update(verbale);
						} catch (Exception e) {
							
							e.printStackTrace();
				        	
				        	request.getSession().setAttribute("exception", e);
				        	myObj = ECIException.callExceptionJsonObject(e);
				        	out.print(myObj);
						}
					}
				}				
				
			}else {
				myObj.addProperty("success", false);
				myObj.addProperty("messaggio", "Attenzione! PIN errato!");
			}
			
			out.print(myObj);
			
		}

		else if(action !=null && action.equals("comuni")) {
			
			String cap = request.getParameter("cap");
			
			ArrayList<ComuneDTO> lista_comuni = GestioneAnagraficaRemotaBO.getListaComuniFromCAP(cap, session);
			
			Gson g = new Gson();
			if(lista_comuni!=null) {
			JsonElement jelem = g.toJsonTree(lista_comuni);
			
			myObj.addProperty("success", true);
			myObj.add("comuni", jelem);
			
			}else {
				myObj.addProperty("success", false);
			}
			
			out.print(myObj);
		}
		else if(action!=null && action.equals("modifica_esercente")) {
		
			
			String esercente = request.getParameter("esercente_mod");
			verbale.setEsercente(esercente);
			session.update(verbale);
			
			myObj.addProperty("success", true);
			myObj.addProperty("messaggio", "Esercente modificato con successo!");

			out.print(myObj);
			
		}
		
		else if(action!=null && action.equals("modifica_descrizione_utilizzatore")) {
		
			
			String descrizione = request.getParameter("descr_util_mod");
			verbale.setDescrizione_sede_utilizzatore(descrizione);
			session.update(verbale);
			
			myObj.addProperty("success", true);
			myObj.addProperty("messaggio", "Descrizione utilizzatore modificata con successo!");

			out.print(myObj);
			
		}
		else if(action!=null && action.equals("modifica_sede_utilizzatore")) {
		
			String sede_utilizzatore = request.getParameter("sede_utilizzatore_mod");
			verbale.setSedeUtilizzatore(sede_utilizzatore);
			session.update(verbale);
			
			myObj.addProperty("success", true);
			myObj.addProperty("messaggio", "Sede utilizzatore modificata con successo!");

			out.print(myObj);
			
		}
		
		else if(action!=null && action.equals("modifica_attrezzatura")) {
		
			
			String id_attrezzatura = request.getParameter("attrezzatura");
			
			if(id_attrezzatura!=null && !id_attrezzatura.equals("0")) {
				AttrezzaturaDTO attrezzatura = GestioneAttrezzatureBO.getAttrezzaturaFromId(Integer.parseInt(id_attrezzatura), session);			
				verbale.setAttrezzatura(attrezzatura);
			}else {
							
				verbale.setAttrezzatura(null);	
			}
			
			session.update(verbale);
			
			myObj.addProperty("success", true);
			myObj.addProperty("messaggio", "Attrezzatura modificata con successo!");

			out.print(myObj);
			
		}
		else if(action!=null && action.equals("carica_verbale_firmato")) {
			

			PrintWriter writer = response.getWriter();
			JsonObject jsono = new JsonObject();
			
			ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = null;
			try {
				items = uploadHandler.parseRequest(request);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String id_certificato = "";
	
			FileItem fileUploaded = null;
			String filename = "";
			for (FileItem item : items) {
				if (!item.isFormField()) {

					fileUploaded = item;
					filename = item.getName();
				}else {
					
					if(item.getFieldName().equals("id_documento")) {
						id_certificato = item.getString();
					}
					

				}
				
			
			}
			
		DocumentoDTO documento = GestioneDocumentoDAO.getDocumento(id_certificato, session);
		verbale = documento.getVerbale();
			
		String filename_pdf = documento.getFilePath().split("\\\\")[2];
		//if(filename!=null && !filename.equals("") && !filename.replace(".p7m", "").equals(filename_pdf) && !filename.replace(".P7M", "").equals(filename_pdf) ) {
		if(filename!=null && !filename.equals("") && !filename.equals(filename_pdf) && !filename.equals(filename_pdf) ) {
			
			jsono.addProperty("success", false);
			jsono.addProperty("messaggio","Attenzione! Il file non corrisponde al verbale generato!");
			
		}else {
			if(fileUploaded != null) {
				
				String path = documento.getFilePath().replace(filename_pdf,"");
				//String path = "Intervento_"+intervento.getId()+File.separator+verbale.getType()+"_"+verbale.getCodiceCategoria()+"_"+verbale.getId()+File.separator;
				new File(Costanti.PATH_CERTIFICATI+path).mkdirs();
				String fileNoExt = filename.substring(0, filename.length()-4);
				File file = new File(Costanti.PATH_CERTIFICATI+path+fileNoExt+"_F.pdf");
				int counter = 0;

					try {
						fileUploaded.write(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
										
					verbale.setFirmato(1);
					session.update(documento);
			
					jsono.addProperty("success", true);
					jsono.addProperty("messaggio","File caricato con successo!");
				}
		}
		
			

			writer.write(jsono.toString());
			writer.close();
		
		}
		
		else if(action!=null && action.equals("email_destinatario")) {			
		
			try {
			String indirizzo = "";
			if(verbale.getIntervento().getIdSede()==0) {
				ClienteDTO cliente = GestioneAnagraficaRemotaBO.getClienteById(""+verbale.getIntervento().getId_cliente());
				indirizzo = cliente.getEmail();
			}else {
				ClienteDTO cliente;
				
					cliente = GestioneAnagraficaRemotaBO.getClienteFromSede(""+verbale.getIntervento().getId_cliente(), ""+verbale.getIntervento().getIdSede());
				
				indirizzo = cliente.getEmail();							
			}
			if(indirizzo == null) {
				indirizzo = "";
			}
			
			myObj.addProperty("success", true);
			myObj.addProperty("indirizzo", indirizzo);
			 out.println(myObj);
			
			} catch (Exception e) {
				
				e.printStackTrace();
	        	
	        	request.getSession().setAttribute("exception", e);
	        	myObj = ECIException.callExceptionJsonObject(e);
	        	out.print(myObj);
			}
			
			
		   
			
		}
		
		else if(action!=null && action.equals("invia_email")) {

			try {
				response.setContentType("text/html");
				
				String indirizzo = request.getParameter("destinatario");
				String id_documento = request.getParameter("id_documento");
			
				DocumentoDTO documento = GestioneDocumentoDAO.getDocumento(id_documento, session);
			
				GestioneComunicazioniBO.sendPecVerbale(documento,documento.getVerbale(), indirizzo);
				
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Email inviata con successo!");
				
				out.println(myObj.toString());
				
			} catch (MessagingException e) {
				
				e.printStackTrace();
	        	
	        	request.getSession().setAttribute("exception", e);
	        	myObj = ECIException.callExceptionJsonObject(e);
	        	out.print(myObj);
			}
				
//				String[] destinatari = indirizzo.replace(" ", "").split(";");
				
//				for (String dest : destinatari) {
//					VerEmailDTO email = new VerEmailDTO();
//					
//					email.setCertificato(certificato);
//					email.setData_invio(new Timestamp(System.currentTimeMillis()));
//					email.setUtente(utente);
//					email.setDestinatario(dest);
//					
//					session.save(email);
//				}
//				
//				certificato.setEmail_inviata(1);
				
//				session.update(certificato);
				
			
			

			
		}
		
		else {
			//caso genericoc della ricerca del verbale per aprire gestioneVerbali					
			List<DomandaVerbaleDTO> domandeVerbale=new ArrayList<DomandaVerbaleDTO>();
			domandeVerbale.addAll(verbale.getDomandeVerbale());
			Collections.sort(domandeVerbale, new Comparator<DomandaVerbaleDTO>() {
				@Override
				public int compare(DomandaVerbaleDTO op2, DomandaVerbaleDTO op1){
					int pos1=op1.getDomandaQuestionario().getPosizione();
					int pos2=op2.getDomandaQuestionario().getPosizione();
					return  pos2 - pos1;
				}
			});

			if(verbale.getSchedaTecnica()!=null) {
				//caso scheda tecnica interna
				List<DomandaVerbaleDTO> domandeVerbaleSchedaTecnica=new ArrayList<DomandaVerbaleDTO>();
				domandeVerbaleSchedaTecnica.addAll(verbale.getSchedaTecnica().getDomandeVerbale());

				Collections.sort(domandeVerbaleSchedaTecnica, new Comparator<DomandaVerbaleDTO>() {
					@Override
					public int compare(DomandaVerbaleDTO op2, DomandaVerbaleDTO op1){
						int pos1=op1.getDomandaQuestionario().getPosizione();
						int pos2=op2.getDomandaQuestionario().getPosizione();
						return  pos2 - pos1;
					}
				});
				
				request.setAttribute("domandeVerbaleSchedaTecnica",domandeVerbaleSchedaTecnica);
				request.getSession().setAttribute("storicoModificheSkTec", GestioneStoricoModificheDAO.getListaStoricoModificheVerbale(verbale.getSchedaTecnica().getId(), session));
				
			}
			
			request.setAttribute("domandeVerbale",domandeVerbale);
			
			QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(verbale.getQuestionarioID(), session);
			request.setAttribute("questionario", questionario);
						
			InterventoDTO intervento=GestioneInterventoDAO.getIntervento(String.valueOf(verbale.getIntervento().getId()),session);
			request.getSession().setAttribute("intervento", intervento);
			request.getSession().setAttribute("verbale", verbale);
			request.getSession().setAttribute("storicoModificheVerb", GestioneStoricoModificheDAO.getListaStoricoModificheVerbale(verbale.getId(), session));
						
			request.setAttribute("hibernateSession", session);
			
			List<DocumentoDTO> listaAllegati = new ArrayList<DocumentoDTO>();
			List<DocumentoDTO> listaCertificati = new ArrayList<DocumentoDTO>();
			List<DocumentoDTO> listaSchedeTecniche = new ArrayList<DocumentoDTO>();
			for(DocumentoDTO doc: verbale.getDocumentiVerbale()) {
	        	if(doc.getType().equalsIgnoreCase(DocumentoDTO.ATTACHMENT)) {
	        		listaAllegati.add(doc);
	        	} else if (doc.getType().equalsIgnoreCase(DocumentoDTO.CERTIFIC)) {
	        		listaCertificati.add(doc);
	        	}
 			}
			if (verbale.getSchedaTecnica() != null) {
				for(DocumentoDTO st: verbale.getSchedaTecnica().getDocumentiVerbale()) {
					if (st.getType().equalsIgnoreCase(DocumentoDTO.SK_TEC)) {
		        		listaSchedeTecniche.add(st);
		        	}
	 			}
			}
			
			
			CompanyDTO cmp=(CompanyDTO)request.getSession().getAttribute("usrCompany");
			
			String idCompany=""+cmp.getId();
			
			List<ClienteDTO> listaClientiFull =(List<ClienteDTO>) request.getSession().getAttribute("listaClienti");
			List<SedeDTO> listaSedi = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");
			
			ArrayList<AttrezzaturaDTO> listaAttrezzature = (ArrayList<AttrezzaturaDTO>) request.getSession().getAttribute("listaAttrezzature");
			
			
			try {
				
				if(listaAttrezzature==null || listaAttrezzature.size()==0) {
					CommessaDTO commessa = GestioneCommesseBO.getCommessaById(verbale.getIntervento().getIdCommessa());
					listaAttrezzature =GestioneAttrezzatureBO.getlistaAttrezzatureSede(commessa.getID_ANAGEN_UTIL(), commessa.getK2_ANAGEN_INDR_UTIL(), false, session);	
				}
			
				if(listaClientiFull==null) {
					listaClientiFull = GestioneAnagraficaRemotaBO.getListaClienti(idCompany);	
				}
				
				if(listaSedi==null) {
					listaSedi = GestioneAnagraficaRemotaBO.getListaSedi();	
				}
			
			} catch (Exception ex) {
				ex.printStackTrace();
				session.getTransaction().rollback();
				session.close();
		   		request.setAttribute("error",ECIException.callException(ex));
		   	    request.getSession().setAttribute("exception", ex);
		   		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/error.jsp");
		   	    dispatcher.forward(request,response);	
			}				

			request.getSession().setAttribute("listaClienti",listaClientiFull);

			request.getSession().setAttribute("listaSedi",listaSedi);	
			
			request.getSession().setAttribute("listaAllegati", listaAllegati);
			request.getSession().setAttribute("listaCertificati", listaCertificati);
			request.getSession().setAttribute("listaSchedeTecniche", listaSchedeTecniche);
			request.getSession().setAttribute("listaAttrezzature", listaAttrezzature);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneVerbale.jsp");
			dispatcher.forward(request,response);
		}	
		session.getTransaction().commit();
		session.close();	
	}
	
	private static byte[] loadFileForBase64(File file) throws IOException {
	    InputStream is = new FileInputStream(file);
	    long length = file.length();
	    byte[] bytes = new byte[(int)length];
	    if (length > Integer.MAX_VALUE) {
		    bytes = new byte[0];       
	    } else {    
		    int offset = 0;
		    int numRead = 0;
		    while (offset < bytes.length
		           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
		        offset += numRead;
		    }	
		    if (offset < bytes.length) {
		    	is.close();
		        throw new IOException("Could not completely read file "+file.getName());
		    }
	    }
		is.close();
		return bytes;
	}
	    
	public void addStorico(VerbaleDTO verbale, String vecchioValore, String nomeCampo, int idRisposta, String azione, HttpServletRequest request, Session session){
		UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
		
		StoricoModificheDTO nuovoSt=new StoricoModificheDTO();
		nuovoSt.setAzione(azione);		
		
		Date datacreazione= new java.sql.Date((new Date(System.currentTimeMillis())).getTime());		
		nuovoSt.setCreateDate(datacreazione);
		nuovoSt.setIdRisposta(idRisposta);
		nuovoSt.setNomeCampo(nomeCampo);
		nuovoSt.setNominativo(user.getNominativo());
		nuovoSt.setUsername(user.getUser());
		nuovoSt.setVecchioValore(vecchioValore);
		nuovoSt.setVerbale(verbale);
		
		GestioneStoricoModificheDAO.save(nuovoSt, session);
	}
	
	private static boolean isInt(String strNum) {
		try {
			double d = Integer.parseInt(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
			return true;
		}
	
}