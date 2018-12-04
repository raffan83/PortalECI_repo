package it.portalECI.DTO;

import java.util.Set;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ColonnaTabellaVerbaleDTO {
	
	private Long id;
	
	private ColonnaTabellaQuestionarioDTO colonnaQuestionario;
	
	private RispostaTabellaVerbaleDTO rispostaParent;
	
	private DomandaVerbaleDTO domanda;
	
	private Set<RispostaVerbaleDTO> risposte;

	public ColonnaTabellaVerbaleDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RispostaTabellaVerbaleDTO getRispostaParent() {
		return rispostaParent;
	}

	public void setRispostaParent(RispostaTabellaVerbaleDTO rispostaParent) {
		this.rispostaParent = rispostaParent;
	}

	public DomandaVerbaleDTO getDomanda() {
		return domanda;
	}

	public void setDomanda(DomandaVerbaleDTO domanda) {
		this.domanda = domanda;
	}

	public ColonnaTabellaQuestionarioDTO getColonnaQuestionario() {
		return colonnaQuestionario;
	}

	public void setColonnaQuestionario(ColonnaTabellaQuestionarioDTO colonnaQuestionario) {
		this.colonnaQuestionario = colonnaQuestionario;
	}

	public Set<RispostaVerbaleDTO> getRisposte() {
		return risposte;
	}

	public void setRisposte(Set<RispostaVerbaleDTO> risposte) {
		this.risposte = risposte;
	}

	public JsonElement getJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.getId());
		jobj.addProperty("larghezza", this.getColonnaQuestionario().getLarghezza());
		jobj.addProperty("posizione", this.getColonnaQuestionario().getPosizione());
		jobj.add("domanda", this.getDomanda().getDomandaJsonObject());
		
		
		return jobj;
	}

}
