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
		JsonObject myObj = new JsonObject();
		PrintWriter  out = response.getWriter();
		
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		List<StoricoModificheDTO> list=GestioneStoricoModificheDAO.getListaStoricoModificheRisposta(Integer.valueOf(request.getParameter("idRisposta")), session);
		RispostaVerbaleDTO risp = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaVerbaleDTO.class,list.get(0).getIdRisposta(),session);
		String dataobject="";
		if( risp.getTipo().equals(RispostaVerbaleDTO.TIPO_TESTO)) {
			dataobject="<table style='width:100%'>" + 
				"<tr>" + 
				"<th style='text-align:center; border:1px solid;'>Vecchio Valore</th>" + 
				"<th style='text-align:center; border:1px solid;'>Utente</th>" + 
				"<th style='text-align:center; border:1px solid;'>Data</th>" + 
				"</tr>";
				 
		
			for (int i = 0; i < list.size(); i++) {
				dataobject+="<tr style='text-align:center; border:1px solid;'>" + 
					"<td style='text-align:center; border:1px solid;'>"+list.get(i).getVecchioValore()+"</td>" + 
					"<td style='text-align:center; border:1px solid;'>"+list.get(i).getUsername()+"</td>" + 
					"<td style='text-align:center; border:1px solid;'>"+list.get(i).getCreateDate()+"</td>" + 
					"</tr>";
			}
		
			dataobject+="</table>";
		}else if( risp.getTipo().equals(RispostaVerbaleDTO.TIPO_FORMULA)) {
			dataobject="<table style='width:100%'>" + 
				"<tr>" + 
				"<th style='text-align:center; border:1px solid;'>Vecchio Valore1</th>" +
				"<th style='text-align:center; border:1px solid;'>Vecchio Valore2</th>" + 
				"<th style='text-align:center; border:1px solid;'>Vecchio Risultato</th>" + 
				"<th style='text-align:center; border:1px solid;'>Utente</th>" + 
				"<th style='text-align:center; border:1px solid;'>Data</th>" + 
				"</tr>";
					 
			
			for (int i = 0; i < list.size(); i++) {
				String[] result =new String[3];
				result=list.get(i).getVecchioValore().split("|");
				String valueresult="";
				try {
					valueresult=result[2];
				} catch (Exception e) {
					valueresult="";
				}
				
				dataobject+="<tr style='text-align:center; border:1px solid;'>" + 
					"<td style='text-align:center; border:1px solid;'>"+result[0]+"</td>" +
					"<td style='text-align:center; border:1px solid;'>"+result[1]+"</td>" +
					"<td style='text-align:center; border:1px solid;'>"+valueresult+"</td>" +
					"<td style='text-align:center; border:1px solid;'>"+list.get(i).getUsername()+"</td>" + 
					"<td style='text-align:center; border:1px solid;'>"+list.get(i).getCreateDate()+"</td>" + 
					"</tr>";
			}
			
			dataobject+="</table>";
			
		}else if( risp.getTipo().equals(RispostaVerbaleDTO.TIPO_SCELTA)) {
			
			
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