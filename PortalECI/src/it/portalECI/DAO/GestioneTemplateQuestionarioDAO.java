package it.portalECI.DAO;

import org.hibernate.Session;
import it.portalECI.DTO.TemplateQuestionarioDTO;

public class GestioneTemplateQuestionarioDAO {
	
	public static TemplateQuestionarioDTO getTemplateById(Integer idTemplateQuestionario, Session session) {
		TemplateQuestionarioDTO template = (TemplateQuestionarioDTO) session.get( TemplateQuestionarioDTO.class, idTemplateQuestionario);
		return template;
	}

}
