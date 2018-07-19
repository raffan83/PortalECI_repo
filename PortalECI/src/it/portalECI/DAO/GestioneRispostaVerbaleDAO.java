package it.portalECI.DAO;

import org.hibernate.Session;

import it.portalECI.DTO.OpzioneRispostaVerbaleDTO;
import it.portalECI.DTO.RispostaQuestionario;
import it.portalECI.DTO.RispostaVerbaleDTO;

public class GestioneRispostaVerbaleDAO {
	


	public static <T extends RispostaVerbaleDTO> void save(T risposta, Session session) {
		session.save(risposta);
	}

	public static void saveOpzioneVerbale(OpzioneRispostaVerbaleDTO opzioneRispostaVerbaleDTO, Session session) {
		session.save(opzioneRispostaVerbaleDTO);
		
	}

}
