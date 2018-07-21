package it.portalECI.DTO;

import org.hibernate.Session;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.portalECI.DAO.SessionFacotryDAO;

public class DomandaVerbaleDTO {
	
	private int id;
	private DomandaQuestionarioDTO domandaQuestionario;
	private RispostaVerbaleDTO risposta;
	private VerbaleDTO verbale;
	
	public DomandaVerbaleDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DomandaQuestionarioDTO getDomandaQuestionario() {
		return domandaQuestionario;
	}

	public void setDomandaQuestionario(DomandaQuestionarioDTO domandaQuestionario) {
		this.domandaQuestionario = domandaQuestionario;
	}

	public RispostaVerbaleDTO getRisposta() {
		return risposta;
	}		

	public void setRisposta(RispostaVerbaleDTO risposta) {
		this.risposta = risposta;
	}

	public VerbaleDTO getVerbale() {
		return verbale;
	}

	public void setVerbale(VerbaleDTO verbale) {
		this.verbale= verbale;
	}

	public JsonElement getDomandaJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.getId());
		jobj.addProperty("testo", this.getDomandaQuestionario().getTesto());
		jobj.addProperty("obbligatoria", this.getDomandaQuestionario().getObbligatoria());
		jobj.addProperty("posizione", this.getDomandaQuestionario().getPosizione());
		
		if(risposta!=null) {
			
			jobj.add("risposta", this.risposta.getJsonObject());
			
		}
			
//		if(this.risposta!=null) {
//			JsonArray domandeVerbaleobj = new JsonArray();
//			
//			for(RispostaVerbaleDTO domanda : this.risposta) {
//				domandeVerbaleobj.add(domanda.getDomandaJsonObject());
//			}
//			jobj.add("domande", domandeVerbaleobj);
//		}
		
		
		
		return jobj;
	}
	
	
	public RispostaTestoVerbaleDTO getRispostaTesto() {
		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		RispostaTestoVerbaleDTO risp =(RispostaTestoVerbaleDTO) session.get(RispostaTestoVerbaleDTO.class, risposta.getId());
		
		session.getTransaction().commit();
		session.close();	
		
		return risp;
	}
	
	public RispostaSceltaVerbaleDTO getRispostaScelta() {
		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		RispostaSceltaVerbaleDTO risp =(RispostaSceltaVerbaleDTO) session.get(RispostaSceltaVerbaleDTO.class, risposta.getId());
		
		session.getTransaction().commit();
		session.close();	
		
		return risp;
	}
	
	public RispostaFormulaVerbaleDTO getRispostaFormula() {
		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		RispostaFormulaVerbaleDTO risp =(RispostaFormulaVerbaleDTO) session.get(RispostaFormulaVerbaleDTO.class, risposta.getId());
		
		session.getTransaction().commit();
		session.close();	
		
		return risp;
	}
	
	
}
