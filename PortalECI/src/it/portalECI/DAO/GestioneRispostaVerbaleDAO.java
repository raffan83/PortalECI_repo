package it.portalECI.DAO;

import org.hibernate.Session;

import it.portalECI.DTO.ColonnaTabellaVerbaleDTO;
import it.portalECI.DTO.OpzioneRispostaVerbaleDTO;
import it.portalECI.DTO.RispostaQuestionario;
import it.portalECI.DTO.RispostaSceltaVerbaleDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;

public class GestioneRispostaVerbaleDAO {
	
	public static <T extends RispostaVerbaleDTO> T getRispostaInstance(Class<T> class1, int id,Session session) {
		return (T) session.get(class1, id);
	}

	public static <T extends RispostaVerbaleDTO> void save(T risposta, Session session) {
		session.saveOrUpdate(risposta);
	}

	public static void saveOpzioneVerbale(OpzioneRispostaVerbaleDTO opzioneRispostaVerbaleDTO, Session session) {
		session.saveOrUpdate(opzioneRispostaVerbaleDTO);
		
	}
	
	public static OpzioneRispostaVerbaleDTO getOpzioneVerbale(int id, Session session) {
		return (OpzioneRispostaVerbaleDTO) session.get(OpzioneRispostaVerbaleDTO.class, id);
		
	}
	
	public static RispostaSceltaVerbaleDTO getRispostaSceltaVerbaleDTO(int id, Session session) {
		return (RispostaSceltaVerbaleDTO) session.get(RispostaSceltaVerbaleDTO.class, id);
		
	}

	public static ColonnaTabellaVerbaleDTO getColonnaVerble(int id, Session session) {
		return (ColonnaTabellaVerbaleDTO) session.get(ColonnaTabellaVerbaleDTO.class, id);
	}

}
