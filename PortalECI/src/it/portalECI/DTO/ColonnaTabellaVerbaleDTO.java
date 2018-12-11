package it.portalECI.DTO;

import java.util.List;
import java.util.Set;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ColonnaTabellaVerbaleDTO {
	
	private int id;
	
	private ColonnaTabellaQuestionarioDTO colonnaQuestionario;
	
	private RispostaTabellaVerbaleDTO rispostaParent;
	
	private DomandaVerbaleDTO domanda;
	
	private List<RispostaVerbaleDTO> risposte;

	public ColonnaTabellaVerbaleDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public List<RispostaVerbaleDTO> getRisposte() {
		return risposte;
	}

	public void setRisposte(List<RispostaVerbaleDTO> risposte) {
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
