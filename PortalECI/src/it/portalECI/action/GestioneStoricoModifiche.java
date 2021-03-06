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
import it.portalECI.DAO.GestioneStoricoModificheDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.DomandaVerbaleDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.OpzioneRispostaVerbaleDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaVerbaleDTO;
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
@WebServlet(name = "gestioneStoricoModifiche", urlPatterns = { "/gestioneStoricoModifiche.do" })
public class GestioneStoricoModifiche extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneStoricoModifiche() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		JsonObject myObj = new JsonObject();
		PrintWriter  out = response.getWriter();
		
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		List<StoricoModificheDTO> list=GestioneStoricoModificheDAO.getListaStoricoModificheRisposta(Integer.valueOf(request.getParameter("idRisposta")), session);
		RispostaVerbaleDTO risp = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaVerbaleDTO.class,list.get(0).getIdRisposta(),session);
		String dataobject="";
		if( risp.getTipo().equals(RispostaVerbaleDTO.TIPO_TESTO)) {
			dataobject="<table class='table table-bordered table-collapsed'>" + 
				"<tr>" + 
				"<th >Data</th>" + 
				"<th >Utente</th>" + 
				"<th >Vecchio Valore</th>" + 
				"</tr>";
				 
		
			for (int i = 0; i < list.size(); i++) {
				dataobject+="<tr >" + 
					"<td >"+list.get(i).getCreateDateFormat()+"</td>" + 
					"<td >"+list.get(i).getUsername()+"</td>" + 
					"<td >"+list.get(i).getVecchioValore()+"</td>" + 
					"</tr>";
			}
		
			dataobject+="</table>";
		}else if( risp.getTipo().equals(RispostaVerbaleDTO.TIPO_FORMULA)) {
			dataobject="<table class='table table-bordered table-collapsed'>" + 
				"<tr>" + 
				"<th >Utente</th>" + 
				"<th >Data</th>" + 
				"<th >Vecchio Valore1</th>" +
				"<th >Vecchio Valore2</th>" + 
				"<th >Vecchio Risultato</th>" + 
				"</tr>";
					 
			
			for (int i = 0; i < list.size(); i++) {
				String[] result = list.get(i).getVecchioValore().split("\\|");		
				String val1="";String val2="";String resVal="";
				if (result.length==3) {
					if(!result[0].equals("null")) val1=result[0];
					if(!result[1].equals("null")) val2=result[1];
					if(!result[2].equals("null")) resVal=result[2];
				}
				
				dataobject+="<tr >" + 
					"<td >"+list.get(i).getUsername()+"</td>" + 
					"<td >"+list.get(i).getCreateDateFormat()+"</td>" + 
					"<td >"+val1+"</td>" +
					"<td >"+val2+"</td>" +
					"<td >"+resVal+"</td>" +
					"</tr>";
			}
			
			dataobject+="</table>";
			
		}else if( risp.getTipo().equals(RispostaVerbaleDTO.TIPO_SCELTA)) {
			dataobject="<table class='table table-bordered table-collapsed'>" + 
					"<tr>" + 
					"<th>Data</th>" + 
					"<th>Utente</th>" + 
					"<th>Opzioni selezionate in precedenza</th>" + 
					"</tr>";
					 
			
				for (int i = 0; i < list.size(); i++) {
					String vecchioValore = "";
					if(!list.get(i).getVecchioValore().isEmpty()) {
						String[] result = list.get(i).getVecchioValore().split("\\|");			
						for (int j = 0; j < result.length; j++) {
							int idOpzione = Integer.parseInt(result[j]);
							OpzioneRispostaVerbaleDTO opzione = GestioneRispostaVerbaleDAO.getOpzioneVerbale(idOpzione, session);
							String testo = opzione.getOpzioneQuestionario().getTesto();
							vecchioValore+= testo+"<br/>";
						}
					}
									
					dataobject+="<tr style='border:1px solid;'>" + 
						"<td>"+list.get(i).getCreateDateFormat()+"</td>" + 
						"<td>"+list.get(i).getUsername()+"</td>" + 
						"<td>"+vecchioValore+"</td>" + 
						"</tr>";
				}
			
			
		}else if( risp.getTipo().equals(RispostaVerbaleDTO.TIPO_TABELLA)) {
			for (int i = 0; i < list.size(); i++) {
				dataobject+="<p>Riga eliminata il "+list.get(i).getCreateDateFormat()+" da "+list.get(i).getUsername()+"</p>";
				dataobject+="<table class='table table-bordered table-collapsed'>" + 
						"<tr>"+list.get(i).getVecchioValore()+"</tr>"+"</table>";
						
			}
		}
		
		
		myObj.addProperty("success", true);
		myObj.addProperty("dataobject",dataobject);
		out.print(myObj);
	}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}