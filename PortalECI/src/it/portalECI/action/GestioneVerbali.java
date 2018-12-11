package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DAO.GestioneInterventoDAO;
import it.portalECI.DAO.GestioneRispostaVerbaleDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.GestioneStoricoModificheDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.ColonnaTabellaVerbaleDTO;
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
import it.portalECI.DTO.StoricoModificheDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.Utility;
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
			
			Enumeration<String> parameterNames = request.getParameterNames(); //lista id elementi modificati

			ArrayList<String> listaFormulaAggiornate=new ArrayList<String>();
		
			while (parameterNames.hasMoreElements()) {
				String paramName = parameterNames.nextElement();

				String id="";
				
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
							
							addStorico(verbale, 
									val1+"|"+ val2+"|"+respVal , 
									null, 
									rispostaFormula.getId(), 
									StoricoModificheDTO.UPDATE, 
									request, 
									session);
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
						addStorico(verbale, oldCheck, null, Integer.parseInt(idrispostaSceltaVerbale), StoricoModificheDTO.UPDATE, request, session);
					}						
				
				}else if(isInt(paramName)){			
					
					String testo=request.getParameter(paramName);

					RispostaTestoVerbaleDTO rispostaTesto = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaTestoVerbaleDTO.class, Integer.parseInt(paramName), session);
					String rispostaTestoVal = rispostaTesto.getResponseValue();

					if (rispostaTestoVal == null) rispostaTestoVal = new String();
					
						if(!rispostaTestoVal.equals(testo)) {
							addStorico(verbale, 
									rispostaTestoVal, 
									null, 
									rispostaTesto.getId(), 
									StoricoModificheDTO.UPDATE, 
									request, 
									session);
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
				        addStorico(verbale, pair.getValue(), "riga", Integer.parseInt(idRispostaTabellaId), StoricoModificheDTO.DELETE, request, session);
			        }
			    }
			}	
		
			for (DomandaVerbaleDTO domanda:verbale.getDomandeVerbale()) {

				if(domanda.getRisposta().getTipo().equals(RispostaVerbaleDTO.TIPO_SCELTA)) {
			
					int valueRicercato=domanda.getRisposta().getId();

					if(!request.getParameterMap().containsKey("options"+String.valueOf(valueRicercato))){			

						if(domanda.getDomandaQuestionario().getObbligatoria()) {
							myObj.addProperty("success", false);
							myObj.addProperty("messaggio", "La domanda '"+domanda.getDomandaQuestionario().getTesto()+"' � obbligatoria.");
					
							out.print(myObj);
							return;
						}
					
						Set<OpzioneRispostaVerbaleDTO> listaOpzioni= GestioneRispostaVerbaleDAO.getRispostaSceltaVerbaleDTO(valueRicercato, session).getOpzioni();
					
						for (OpzioneRispostaVerbaleDTO s : listaOpzioni) {
							OpzioneRispostaVerbaleDTO opzioneRispostaVerbaleDTO = GestioneRispostaVerbaleDAO.getOpzioneVerbale(s.getId(), session);
							opzioneRispostaVerbaleDTO.setChecked(false);
							GestioneRispostaVerbaleDAO.saveOpzioneVerbale(opzioneRispostaVerbaleDTO, session);
						}		
					}
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
		
		String idVerbale=request.getParameter("idVerbale");
		VerbaleDTO verbale = null;
		if(request.getParameter("idVerbale") != null && (String)request.getParameter("idVerbale")!="" ) {
			verbale = GestioneVerbaleBO.getVerbale(idVerbale, session);
		}
		
		JsonObject myObj = new JsonObject();
		PrintWriter  out = response.getWriter();
		String action=request.getParameter("action");
		
		if(action !=null && action.equals("cambioStato")){			 					
			
			String stato = request.getParameter("stato" );										
			if(Boolean.parseBoolean(request.getParameter("all"))) {
				if(verbale.getSchedaTecnica()!=null)
					GestioneVerbaleBO.cambioStato( verbale.getSchedaTecnica(), GestioneStatoVerbaleDAO.getStatoVerbaleById( Integer.parseInt(stato), session) , session);
			}
			GestioneVerbaleBO.cambioStato( verbale, GestioneStatoVerbaleDAO.getStatoVerbaleById( Integer.parseInt(stato), session) , session);	
			
			myObj.addProperty("success", true);
			myObj.addProperty("messaggio", "Stato modificato con successo");
			
			out.print(myObj);
		} else if(action !=null && action.equals("generaCertificato")) {
			QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(verbale.getQuestionarioID(),session);
			try {
				File certificato = GestioneVerbaleBO.getPDFVerbale(verbale, questionario, session);
				if(certificato != null) {
					byte[] pdfArray = loadFileForBase64(certificato);
					if(pdfArray.length == 0) {
						myObj.addProperty("success", false);
						myObj.addProperty("messaggio","Certificato troppo grande per essere generato!");						
					} else {
						byte[] encoded = Base64.encodeBase64(pdfArray);
						String pdfBytes = new String(encoded);
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
		} else {
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
			request.getSession().setAttribute("listaAllegati", listaAllegati);
			request.getSession().setAttribute("listaCertificati", listaCertificati);
			request.getSession().setAttribute("listaSchedeTecniche", listaSchedeTecniche);
			
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