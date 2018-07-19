package it.portalECI.DAO;

import org.hibernate.Session;

import it.portalECI.DTO.RispostaQuestionario;
import it.portalECI.DTO.RispostaTestoVerbaleDTO;

public class GestioneRispostaQuestionarioDAO {

	public static <T extends RispostaQuestionario> T getRispostaInstance(Class<T> class1, int id,Session session) {
		return (T) session.get(class1, id);
	}

}
