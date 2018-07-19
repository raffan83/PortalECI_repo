package it.portalECI.DTO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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


}
