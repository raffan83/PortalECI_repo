package it.portalECI.bo;

import java.util.List;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneDomandaVerbaleDAO;
import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaVerbaleDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.GestioneTemplateQuestionarioDAO;
import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DTO.DomandaQuestionarioDTO;
import it.portalECI.DTO.DomandaVerbaleDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneTemplateQuestionarioBO {

	public static TemplateQuestionarioDTO getQuestionarioById(Integer idTemplate, Session session) {
		return GestioneTemplateQuestionarioDAO.getTemplateById(idTemplate, session);
	}
	
	
	
	
	

}
