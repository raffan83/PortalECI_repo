package it.portalECI.DTO;

import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OpzioneRispostaVerbaleDTO {
	private int id;
	private OpzioneRispostaQuestionarioDTO opzioneQuestionario;
	private RispostaSceltaVerbaleDTO risposta;
	private Date createDate;
	private Date updateDate;
	
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
		
		return jobj;
	}
	
}
