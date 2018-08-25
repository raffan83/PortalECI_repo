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
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DAO.GestioneInterventoDAO;
import it.portalECI.DAO.GestioneRispostaVerbaleDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.DomandaVerbaleDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.OpzioneRispostaVerbaleDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaVerbaleDTO;
import it.portalECI.DTO.RispostaTestoVerbaleDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JsonObject myObj = new JsonObject();
		PrintWriter  out = response.getWriter();
		
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		try {
			VerbaleDTO verbale = GestioneVerbaleBO.getVerbale(request.getParameter("idVerbale"),session); 
		
			if(verbale.getStato().getId()!= 4) {
				myObj.addProperty("success", false);
				myObj.addProperty("messaggio", "Impossibile modificare un verbale nello stato Accettato o Rifiutato!");
		
				out.print(myObj);
				return;
			
			}
		
			Enumeration<String> parameterNames = request.getParameterNames();
		
			ArrayList listaFormulaAggiornate=new ArrayList();
		
			while (parameterNames.hasMoreElements()) {
				String paramName = parameterNames.nextElement();

				String id="";
				if(paramName.contains("value1") || paramName.contains("value2") || paramName.contains("responseValue")) {				
					id=paramName.replaceAll("value1", "").replaceAll("value2", "").replaceAll("responseValue", "");
				
					if(!listaFormulaAggiornate.contains(id)) {
						listaFormulaAggiornate.add(id);
						String value1=request.getParameter("value1"+id);
						String value2=request.getParameter("value2"+id);
						String responseValue=request.getParameter("responseValue"+id);
				
						RispostaFormulaVerbaleDTO rispostaFormula = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaFormulaVerbaleDTO.class, Integer.parseInt(id), session);
						rispostaFormula.setValue1(value1);
						rispostaFormula.setValue2(value2);
						rispostaFormula.setResponseValue(responseValue);
						GestioneRispostaVerbaleDAO.save(rispostaFormula, session);
					}
				
				}else if(paramName.contains("options")) {			
					String idrispostaSceltaVerbale=paramName.replaceAll("options", "");
			
					Set<OpzioneRispostaVerbaleDTO> listaOpzioni= GestioneRispostaVerbaleDAO.getRispostaSceltaVerbaleDTO(Integer.parseInt(idrispostaSceltaVerbale), session).getOpzioni();
								
					String[] listaid = request.getParameterValues(paramName);
				
					for (OpzioneRispostaVerbaleDTO s : listaOpzioni) {
						Boolean value=false;
						if(Arrays.asList(listaid).contains(String.valueOf(s.getId()))) {
							value=true;
						}
				    
						OpzioneRispostaVerbaleDTO opzioneRispostaVerbaleDTO = GestioneRispostaVerbaleDAO.getOpzioneVerbale(s.getId(), session);
						opzioneRispostaVerbaleDTO.setChecked(value);
						GestioneRispostaVerbaleDAO.saveOpzioneVerbale(opzioneRispostaVerbaleDTO, session);
					}				
											
				
				}else if(!paramName.contains("idVerbale") && !paramName.contains("options") && !paramName.contains("responseValue") && !paramName.contains("value2") && !paramName.contains("value1")){			
					String testo=request.getParameter(paramName);

					RispostaTestoVerbaleDTO rispostaTesto = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaTestoVerbaleDTO.class, Integer.parseInt(paramName), session);
					rispostaTesto.setResponseValue(testo);
					GestioneRispostaVerbaleDAO.save(rispostaTesto, session);

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
			// TODO Auto-generated catch block
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
			}
			
			request.setAttribute("domandeVerbale",domandeVerbale);
			
			QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(verbale.getQuestionarioID(), session);
			request.setAttribute("questionario", questionario);
						
			InterventoDTO intervento=GestioneInterventoDAO.getIntervento(String.valueOf(verbale.getIntervento().getId()),session);
			request.getSession().setAttribute("intervento", intervento);
			request.getSession().setAttribute("verbale", verbale);
			request.setAttribute("hibernateSession", session);
			
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
		        throw new IOException("Could not completely read file "+file.getName());
		    }
	    }
		is.close();
		return bytes;
	}
	    
}