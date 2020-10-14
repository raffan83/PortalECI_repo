package it.portalECI.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneQuestionarioBO;
import it.portalECI.bo.GestioneTipiVerificaBO;
import it.portalECI.bo.GestioneUtenteBO;

/**
 * Servlet implementation class GestioneIntervento
 */
@WebServlet(name = "gestioneInterventoDati", urlPatterns = { "/gestioneInterventoDati.do" })
public class GestioneInterventoDati extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneInterventoDati() {
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
		
		String idIntervento=request.getParameter("idIntervento");
		
		InterventoDTO intervento= GestioneInterventoBO.getIntervento(idIntervento, session);		
		
		ArrayList<TipoVerificaDTO> tipi_verifica = GestioneInterventoBO.getTipoVerifica(session);
		ArrayList<CategoriaVerificaDTO> categorie_verifica = GestioneInterventoBO.getCategoriaVerifica(session);
		List<UtenteDTO> users = GestioneUtenteBO.getTecnici("2", session);
		
		request.getSession().setAttribute("intervento", intervento);
					
	
		request.getSession().setAttribute("tipi_verifica", tipi_verifica);
		request.getSession().setAttribute("categorie_verifica", categorie_verifica);
		request.getSession().setAttribute("tecnici", users);
		
		
		List<Integer> listIdCodiciCategoria = new ArrayList<Integer>();
		List<String> listCodiciCategoria = new ArrayList<String>();
		List<Integer> listIdCodiciTipo = new ArrayList<Integer>();
		List<String> listCodiciTipo = new ArrayList<String>();
		List<Boolean> listEsisteTemplateScTec = new ArrayList<Boolean>();
		List<Boolean> listEsisteScTec = new ArrayList<Boolean>();
		
		for(VerbaleDTO verbale : intervento.getVerbali()) {
			
			if(verbale!=null && verbale.getType().equals(VerbaleDTO.VERBALE)) {
				
				listCodiciCategoria.add(verbale.getCodiceCategoria());
				for(int i=0;i<categorie_verifica.size();i++){
			        if(categorie_verifica.get(i).getCodice().equals(verbale.getCodiceCategoria())){
			        	listIdCodiciCategoria.add(categorie_verifica.get(i).getId());
			            break;
			        }
				}
				TipoVerificaDTO tipoVerifica = null;
				listCodiciTipo.add(verbale.getCodiceVerifica());
				for(int i=0;i<tipi_verifica.size();i++){
			        if(tipi_verifica.get(i).getCodice().equals(verbale.getCodiceVerifica())){
			        	listIdCodiciTipo.add(tipi_verifica.get(i).getId());
			        	tipoVerifica=tipi_verifica.get(i);
			            break;
			        }
				}
				
				try {
					QuestionarioDTO quest= GestioneQuestionarioBO.getLastQuestionarioByVerifica(tipoVerifica, session);
					
					if(quest!= null && quest.getTemplateSchedaTecnica()!=null) {
						listEsisteTemplateScTec.add(true);
					}else {
						listEsisteTemplateScTec.add(false);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if(verbale.getSchedaTecnica()!=null) {
					listEsisteScTec.add(true);
				}else {
					listEsisteScTec.add(false);
				}			
				
			}
		}
	
		request.getSession().setAttribute("listIdCodiciCategoria", listIdCodiciCategoria);
		request.getSession().setAttribute("listCodiciCategoria", listCodiciCategoria);
		request.getSession().setAttribute("listIdCodiciTipo", listIdCodiciTipo);
		request.getSession().setAttribute("listCodiciTipo", listCodiciTipo);
		request.getSession().setAttribute("listEsisteTemplateScTec", listEsisteTemplateScTec);
		request.getSession().setAttribute("listEsisteScTec", listEsisteScTec);
		

		CommessaDTO commessa;
		try {
			commessa = GestioneCommesseBO.getCommessaById(intervento.getIdCommessa());
			request.getSession().setAttribute("commessa", commessa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneInterventoDati.jsp");
		dispatcher.forward(request,response);
		
		session.getTransaction().commit();
		session.close();	
	}
}