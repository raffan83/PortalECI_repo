package it.portalECI.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OpzioneRispostaVerbaleDTO {
	private int id;
	private OpzioneRispostaQuestionarioDTO opzioneQuestionario;
	private RispostaSceltaVerbaleDTO risposta;
	private Date createDate;
	private Date updateDate;
	
	private Set<DomandaVerbaleDTO> domande;
	
	private boolean checked;
	
	public OpzioneRispostaVerbaleDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OpzioneRispostaQuestionarioDTO getOpzioneQuestionario() {
		return opzioneQuestionario;
	}

	public void setOpzioneQuestionario(OpzioneRispostaQuestionarioDTO opzioneQuestionario) {
		this.opzioneQuestionario = opzioneQuestionario;
	}

	public RispostaSceltaVerbaleDTO getRisposta() {
		return risposta;
	}

	public void setRisposta(RispostaSceltaVerbaleDTO risposta) {
		this.risposta = risposta;
	}
	
	

	public Set<DomandaVerbaleDTO> getDomande() {
		return domande;
	}

	public void setDomande(Set<DomandaVerbaleDTO> domande) {
		this.domande = domande;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean isChecked) {
		this.checked = isChecked;
	}

	
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public JsonElement getJsonObject() {
		JsonObject jobj = new JsonObject();
		
		jobj.addProperty("id", this.getId());
		jobj.addProperty("testo", this.getOpzioneQuestionario().getTesto());
		jobj.addProperty("posizione", this.getOpzioneQuestionario().getPosizione());
		
		if(this.domande !=null) {
			JsonArray domandeObj = new JsonArray();
			for(DomandaVerbaleDTO domandaOpzione: this.domande) {
				domandeObj.add(domandaOpzione.getDomandaJsonObject());
			}
			jobj.add("domande", domandeObj);
		}
		return jobj;
	}

	public void addToDomande(DomandaVerbaleDTO domandaOpzioneVerbale) {
		if (this.domande == null) this.domande=new HashSet<DomandaVerbaleDTO>();
		this.domande.add(domandaOpzioneVerbale);
	}
	
}
